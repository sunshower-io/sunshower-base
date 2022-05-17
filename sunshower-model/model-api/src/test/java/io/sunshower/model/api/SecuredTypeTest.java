package io.sunshower.model.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.sunshower.arcus.persist.flyway.ArcusFlywayMigrationManager;
import io.sunshower.arcus.persist.flyway.FlywayTestConfiguration;
import io.sunshower.arcus.persist.hibernate.ArcusPersistenceTest;
import io.sunshower.arcus.persist.hibernate.TestPersistenceConfiguration;
import io.sunshower.persistence.config.DataSourceConfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlywayTestConfiguration.class})
class SecuredTypeTest {


  @PersistenceContext
  EntityManager entityManager;


  @Test
  void ensureEverythingIsConfiguredProperly() {
    assertNotNull(entityManager);
  }

  @Test
  void ensurePersistingSecurityIdentityWorks() {
    val sid = new SecurityIdentity();

    val user = new User();
    user.setUsername("sup");
    user.setPassword("password");
    sid.setOwner(user);
    entityManager.persist(user);
    entityManager.persist(sid);
    entityManager.flush();
    assertNotNull(sid.getId());
  }
}
