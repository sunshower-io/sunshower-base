package io.sunshower.model.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.Test;

@ModelTest
class SecuredTypeTest {

  @PersistenceContext EntityManager entityManager;

  @Test
  void ensureEverythingIsConfiguredProperly() {
    assertNotNull(entityManager);
  }

  @Test
  void ensurePersistingSecurityIdentityWorks() {
    val sid = new SecurityIdentity();

    val tenant = tenant();

    entityManager.persist(tenant);

    val user = new User();
    user.setUsername("sup");
    user.setPassword("password");
    sid.setOwner(user);
    entityManager.persist(user);
    tenant.addUser(user);
    entityManager.persist(sid);
    entityManager.flush();
    assertNotNull(sid.getId());
  }

  private Tenant tenant() {
    val tenant = new Tenant();
    tenant.setName("pepsi");
    return tenant;
  }
}
