package io.sunshower.persist;

import io.sunshower.barometer.jaxrs.SerializationAware;
import io.sunshower.barometer.jaxrs.SerializationTestCase;
import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.hibernate.HibernateConfiguration;
import io.sunshower.persist.hibernate.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by haswell on 2/25/17.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        FlywayConfiguration.class,
        DataSourceConfiguration.class,
        HibernateConfiguration.class
})
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class HibernateTestCase extends SerializationTestCase {

    final Logger logger = Logger.getLogger(getClass().getName());

    private Set<Object> toRemove;
    private Set<Object> toSave;

    @PersistenceContext
    private EntityManager entityManager;

    protected HibernateTestCase(SerializationAware.Format format, Class<?>[] bound) {
        super(format, bound);
        toSave = new HashSet<>();
        toRemove = new HashSet<>();
    }

    protected HibernateTestCase() {
        this(SerializationAware.Format.JSON, new Class[]{});
    }


    protected <T> T save(T value) {
        entityManager.persist(value);
        toRemove.add(value);
        return value;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void clear() {
        for(Object o : toRemove) {
            entityManager.remove(o);
        }
        toRemove.clear();
        toSave.clear();
        entityManager.flush();
    }
}
