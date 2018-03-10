package io.sunshower.persistence.core;

import io.sunshower.persist.HibernateTestCase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import test.entities.hierarchy.Person;

@Transactional
public class HierarchicalEntityTest extends HibernateTestCase {

  @Inject private TestService testService;
  @PersistenceContext private EntityManager entityManager;

  @Test
  public void ensureSavingPersonWorks() {
    final Person person = new Person();
    person.addChild(new Person());
    entityManager.persist(person);
    entityManager.flush();
  }

  @Test
  public void ensuretransactionPropagationWorks() {

    final Person person = new Person();
    person.addChild(new Person());
    testService.saveEntity(person);
  }
}
