package io.sunshower.persist.hibernate;

import io.sunshower.common.Identifier;
import io.sunshower.persist.HibernateTestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import test.entities.one2one.Ownee;
import test.entities.one2one.Owner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by haswell on 2/24/17.
 */
@ContextConfiguration(classes = HibernateConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToOneTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager entityManager;


    static Identifier id;

    @Test
    @Commit
    public void a_saveRevision1() {
        final Owner owner = new Owner();
        owner.setName("frapper");
        entityManager.persist(owner);
        id = owner.getId();

        assertThat(entityManager.find(Owner.class, id), is(not(nullValue())));
    }

    @Test
    @Commit
    public void b_saveRevision2() {
        Owner owner = entityManager.createQuery("select e from Owner e where e.id = :id", Owner.class)
                .setParameter("id", id.value()).getSingleResult();
        Ownee ownee = new Ownee();
        ownee.setName("froadfasdf");
        owner.setOwnee(ownee);
        entityManager.merge(owner);
    }



    @Test
    public void ensureSavingSingleOwnerWorks() {
        final Owner owner = new Owner();
        entityManager.persist(owner);
        entityManager.flush();
    }

    @Test
    public void ensureSavingBidirectionalOwnerWorksWithCascade() {
        Owner owner = new Owner();
        Ownee ownee = new Ownee();
        ownee.setName("coobleans");
        owner.setOwnee(ownee);
        entityManager.persist(owner);
        entityManager.flush();
//        assertThat(owner.getVersion(), is(ownee.getVersion()));
    }
}
