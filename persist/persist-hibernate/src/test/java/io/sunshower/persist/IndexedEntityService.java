package io.sunshower.persist;

import org.hibernate.search.jpa.FullTextEntityManager;

import javax.persistence.EntityManager;


public interface IndexedEntityService {

    EntityManager entityManager();


    FullTextEntityManager getFtEntityManager();
}
