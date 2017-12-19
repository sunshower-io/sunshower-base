package io.sunshower.test.persist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.sunshower.persistence.PersistenceUnit;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import persist.test.TestEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@Configuration
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(classes = {
        TestConfiguration.class,
        HibernateTestCase.class
})
public class EnableJPATest {


    @Test
    public void ensureTestWorks() {


    }


    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PersistenceUnit persistence;


    @Test
    public void ensurePersistenceUnitCanBeInjected() {
        assertThat(persistence, is(not(nullValue())));
    }

    @Test
    public void ensurePersistenceUnitHasOnePersistenceConfiguration() {
        assertThat(persistence.configurations().size(), is(1));
    }

    @Test
    public void ensureEntityCanBePersisted() {
        final TestEntity entity = new TestEntity();
        entityManager.persist(entity);
        entityManager.flush();
    }
}
