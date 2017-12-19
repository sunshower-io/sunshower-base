package io.sunshower.persist;

import io.sunshower.persistence.search.IndexedPersistenceContext;
import io.sunshower.persistence.search.SearchableIndexAware;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by haswell on 3/3/17.
 */
@SearchableIndexAware
public class DefaultIndexedEntityService implements IndexedEntityService {


    @PersistenceContext
    private EntityManager entityManager;


    @Inject
    private FullTextEntityManager fullTextEntityManager;

    @Override
    public EntityManager entityManager() {
        return entityManager;
    }

    @Override
    public FullTextEntityManager getFtEntityManager() {
        return fullTextEntityManager;
    }
}
