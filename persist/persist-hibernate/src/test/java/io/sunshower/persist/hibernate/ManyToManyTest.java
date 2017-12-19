package io.sunshower.persist.hibernate;

import io.sunshower.barometer.spring.BarometerRunner;
import io.sunshower.persist.HibernateTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import test.entities.many2many.BlogEntry;
import test.entities.many2many.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by haswell on 2/25/17.
 */


@ContextConfiguration(classes = TestConfig.class)
public class ManyToManyTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void ensureSavingManyToManyWorks() {
        BlogEntry entry = new BlogEntry();
        entry.addTag(new Tag("Coolbeans"));
        entry.addTag(new Tag("Coolbeans"));
        entityManager.persist(entry);
        entityManager.flush();
    }


}
