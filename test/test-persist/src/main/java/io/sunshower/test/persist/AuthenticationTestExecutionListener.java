package io.sunshower.test.persist;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SuppressWarnings("all")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class AuthenticationTestExecutionListener extends AbstractTestExecutionListener {

  @PersistenceContext private EntityManager entityManager;

  final Map<String, Object> userCache = new HashMap<>();

  public void beforeTestClass(TestContext ctx) {
    ctx.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
  }

  @Override
  public int getOrder() {
    return 9999;
  }

  @Override
  public void beforeTestMethod(TestContext testContext) throws Exception {
    assertThat(entityManager, is(not(nullValue())));
    Map<String, GrantedAuthority> authorities =
        collectRoles(
            entityManager,
            testContext.getTestClass(),
            testContext.getTestInstance(),
            testContext.getApplicationContext());

    saveAuthorities(authorities);
    saveFields(testContext.getTestClass(), testContext.getTestInstance(), authorities);

    saveMethods(testContext.getTestClass(), testContext.getTestInstance(), authorities);
  }

  private void resolveEntityManager(TestContext testContext) {
    EntityManagerFactory emf =
        testContext.getApplicationContext().getBean(EntityManagerFactory.class);
    EntityManagerHolder holder =
        (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
    this.entityManager = holder.getEntityManager();
  }

  private void saveAuthorities(Map<String, GrantedAuthority> authorities) {
    for (GrantedAuthority authority : authorities.values()) {
      entityManager.persist(authority);
    }
    entityManager.flush();
  }

  private Map<String, GrantedAuthority> collectRoles(
      EntityManager bean,
      Class<?> testClass,
      Object testInstance,
      ApplicationContext applicationContext) {

    final Map<String, GrantedAuthority> authorities = new HashMap<>();

    try {
      collectRolesOnMethods(bean, testClass, testInstance, authorities);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return authorities;
  }

  private void collectRolesOnMethods(
      EntityManager bean,
      Class<?> testClass,
      Object testInstance,
      Map<String, GrantedAuthority> authorities)
      throws InvocationTargetException, IllegalAccessException {

    for (Class<?> current = testClass; current != null; current = current.getSuperclass()) {
      for (Method m : current.getDeclaredMethods()) {
        if (m.isAnnotationPresent(Authority.class)) {
          if (m.getParameterCount() == 0) {
            Class<?> type = m.getReturnType();
            checkType(current, m, type);
            m.setAccessible(true);
            GrantedAuthority authority = (GrantedAuthority) m.invoke(testInstance);
            final String name = authority.getAuthority();
            authorities.put(name, authority);
          } else {
            throw new InvalidTestException(
                String.format(
                    "Method '%s' on type '%s' has " + "more than zero arguments", m, current));
          }
        }
      }
    }
  }

  private void checkType(Class<?> current, Method m, Class<?> type) {
    if (!GrantedAuthority.class.isAssignableFrom(type)) {
      throw new InvalidTestException(
          String.format(
              "Method '%s' on type '%s' has "
                  + "return type '%s', which is not "
                  + "assignable from GrantedAuthority",
              m, current, type));
    }
  }

  private void collectRolesOnFields(
      EntityManagerFactory bean,
      Class<?> testClass,
      Object testInstance,
      Map<String, GrantedAuthority> repository) {}

  private void saveMethods(
      Class<?> testClass, Object testInstance, Map<String, GrantedAuthority> authorities) {
    Set<Object> users = usersFromMethods(testClass, testInstance, authorities);
    saveAll(users, testClass);
  }

  private void saveFields(
      Class<?> testClass, Object instance, Map<String, GrantedAuthority> authorities) {
    Set<Object> users = collectUsers(testClass, instance);
    saveAll(users, testClass);
  }

  private Set<Object> usersFromMethods(
      Class<?> testClass, Object testInstance, Map<String, GrantedAuthority> authorities) {

    Set<Object> users = new HashSet<>();
    for (Class<?> type = testClass; type != null; type = type.getSuperclass()) {
      for (Method f : type.getDeclaredMethods()) {
        if (f.isAnnotationPresent(Principal.class)) {
          f.setAccessible(true);
          GrantedAuthority[] arguments = resolveAuthorities(f, authorities);
          try {
            Object user = f.invoke(testInstance, (Object[]) arguments);
            users.add(user);
          } catch (Exception e) {
            throw new IllegalStateException(e);
          }
        }
      }
    }
    return dedupe(users);
  }

  private GrantedAuthority[] resolveAuthorities(
      Method f, Map<String, GrantedAuthority> authorities) {
    Parameter[] parameters = f.getParameters();
    GrantedAuthority[] results = new GrantedAuthority[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      Parameter p = parameters[i];
      Authority a = p.getAnnotation(Authority.class);

      GrantedAuthority o = findAuthority(a, authorities);
      results[i] = o;
    }
    return results;
  }

  private GrantedAuthority findAuthority(Authority a, Map<String, GrantedAuthority> authorities) {
    GrantedAuthority authority = authorities.get(a.value());
    if (authority == null) {
      throw new IllegalStateException("Dunno what's up with this");
    }
    final Class<GrantedAuthority> type =
        (Class<GrantedAuthority>) AopUtils.getTargetClass(authority);
    final String simpleName = type.getSimpleName();
    final String query =
        String.format("select a " + "from %s as a where " + "a.authority = :authority", simpleName);
    final List<GrantedAuthority> result =
        entityManager.createQuery(query, type).setParameter("authority", a.value()).getResultList();
    if (result.isEmpty()) {
      throw new NoSuchElementException(
          String.format(
              "No authority named '%s' "
                  + "found--did you created it with an @Authority "
                  + "annotation somewhere in your test context?",
              a.value()));
    }
    return result.get(0);
  }

  private synchronized void saveAll(Set<Object> users, Class<?> testClass) {
    for (Object user : users) {
      String id = id(user);
      entityManager.persist(user);
    }
    entityManager.flush();
  }

  // Meh.  Don't  clutter the classpath with api

  private String id(Object o) {
    try {
      Field id = o.getClass().getDeclaredField("username");
      id.setAccessible(true);
      return (String) id.get(o);

    } catch (Exception e) {
      throw new IllegalStateException();
    }
  }

  private Set<Object> collectUsers(Class<?> testClass, Object instance) {
    final Set<Object> users = new HashSet<>();
    for (Class<?> type = testClass; type != null; type = type.getSuperclass()) {
      for (Field f : type.getDeclaredFields()) {
        if (f.isAnnotationPresent(Principal.class)) {
          f.setAccessible(true);
          try {
            Object user = f.get(instance);
            users.add(user);
          } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
          }
        }
      }
    }
    return dedupe(users);
  }

  private Set<Object> dedupe(Set<Object> users) {
    final Set<String> usernames = new HashSet<>();
    return users.stream().filter(t -> usernames.add(id(t))).collect(Collectors.toSet());
  }
}
