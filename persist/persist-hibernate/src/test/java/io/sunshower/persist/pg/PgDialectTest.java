package io.sunshower.persist.pg;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.hibernate.HibernateConfiguration;
import io.sunshower.persist.hibernate.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import io.sunshower.persist.HibernateTestCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@ActiveProfiles("postgres")
@TestConfiguration("classpath:application-postgres.yml")
@Transactional
@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(classes = {
        FlywayConfiguration.class,
        DataSourceConfiguration.class,
        HibernateConfiguration.class,
        PgCfg.class
})
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PgDialectTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensurePgTestEntityCanBeSaved() {
        this.entityManager.persist(new TestEntity());
    }
}
