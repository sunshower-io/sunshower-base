package io.sunshower.persistence.core;

import io.sunshower.persist.HibernateTestCase;
import org.junit.jupiter.api.Test;
import test.entities.hierarchy.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class HierarchicalEntityTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensureSavingPersonWorks() {
        final Person person = new Person();
        person.addChild(new Person());
        entityManager.persist(person);
        entityManager.flush();
    }
}
