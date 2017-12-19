package io.sunshower.persist.hibernate;

import io.sunshower.barometer.spring.BarometerRunner;
import io.sunshower.persist.HibernateTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.entities.one2many.Owned;
import test.entities.one2many.Owner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by haswell on 2/24/17.
 */
@ContextConfiguration(classes = TestConfig.class)
public class OneToManyTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensureSavingOwnerWithChildrenWorks() {
        Owner owner = new Owner();
        owner.addOwned(new Owned());
        entityManager.persist(owner);
        entityManager.flush();
    }

    @Test
    public void ensureSavingIndividualOwnerWorks() {
        Owner owner = new Owner();
        entityManager.persist(owner);
        entityManager.flush();
    }
}
