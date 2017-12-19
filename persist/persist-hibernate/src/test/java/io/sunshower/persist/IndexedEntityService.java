package io.sunshower.persist;

import org.hibernate.search.jpa.FullTextEntityManager;

import javax.persistence.EntityManager;

/**
 * Created by haswell on 3/3/17.
 */
public interface IndexedEntityService {

    EntityManager entityManager();


    FullTextEntityManager getFtEntityManager();
}
