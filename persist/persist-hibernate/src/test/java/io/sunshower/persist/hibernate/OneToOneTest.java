package io.sunshower.persist.hibernate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import io.sunshower.common.Identifier;
import io.sunshower.persist.HibernateTestCase;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import test.entities.one2one.Ownee;
import test.entities.one2one.Owner;

@ContextConfiguration(classes = HibernateConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToOneTest extends HibernateTestCase {

  @PersistenceContext private EntityManager entityManager;

  static Identifier id;

  @BeforeEach
  public void validateDb() {
    List objects = entityManager.createQuery("select o from Owner o").getResultList();
    assertThat(objects.isEmpty(), is(true));
  }

  @Test
  public void a_saveRevision1() {
    final Owner owner = new Owner();
    owner.setName("frapper");
    entityManager.persist(owner);
    id = owner.getId();
    entityManager.flush();

    assertThat(entityManager.find(Owner.class, id), is(not(nullValue())));
  }

  @Test
  public void b_saveRevision2() {
    Owner owner = new Owner();
    owner.setName("frapper");
    entityManager.persist(owner);
    entityManager.flush();
    owner =
        entityManager
            .createQuery("select e from Owner e where e.id = :id", Owner.class)
            .setParameter("id", owner.getId().value())
            .getSingleResult();
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
  }
}
