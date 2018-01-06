package io.sunshower.persist.pg;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.hibernate.HibernateConfiguration;
import io.sunshower.test.common.TestConfigurationConfiguration;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Transactional
@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(classes = {
        FlywayConfiguration.class,
        DataSourceConfiguration.class,
        HibernateConfiguration.class,
        PgCfg.class,
        TestConfigurationConfiguration.class
        
})
@DirtiesContext
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PgDialectTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensurePgTestEntityCanBeSaved() {
        this.entityManager.persist(new TestEntity());
    }
}
