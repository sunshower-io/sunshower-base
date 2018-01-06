package io.sunshower.persist;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import test.entities.IndexedEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@Transactional
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexedEntityTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private IndexedEntityService entityService;

    @Inject
    private FullTextEntityManager entityManager;
    private static IndexedEntity entity;

//    @Test
//    public void ensureEntityManagerIsInjectedIntoThis() {
//        assertThat(entityManager, is(not(nullValue())));
//
//    }
//
//    @Test
//    public void ensureServiceIsInjected() {
//        assertThat(entityService, is(notNullValue()));
//
//    }
//
//    @Test
//    public void ensureEntityManagerIsInjected() {
//        assertThat(entityService.getFtEntityManager(), is(not(nullValue())));
//    }
//
//    @Test
//    public void a_ensureEntityManagerIsSearchable() {
//        entity = new IndexedEntity();
//        entity.setName("testalotofthingsthisiscool");
//        em.persist(entity);
//        em.flush();
//    }

    @Test
    public void b_ensureEntityManagerIsSearchable() throws InterruptedException {
        try {

            FullTextEntityManager entityManager = Search.getFullTextEntityManager(em);

            QueryBuilder queryBuilder = entityManager
                    .getSearchFactory().buildQueryBuilder()
                    .forEntity(IndexedEntity.class)
                    .get();
            entity = new IndexedEntity();
            entity.setName("cool");
            em.persist(entity);
            em.flush();
            entityManager.flushToIndexes();

            Query query = queryBuilder.keyword()
                    .wildcard()
                    .onFields("name")
                    .matching("cool").createQuery();

            List<IndexedEntity> resultList = entityManager
                    .createFullTextQuery(query, IndexedEntity.class)
                    .getResultList();
            assertThat(resultList.size(), is(1));
        } finally {
            entityManager.purgeAll(IndexedEntity.class);
        }
    }
    

//    @Test
//    public void b_ensureEntityManagerIsSearchable_fuzzy() throws InterruptedException {
//
//        entityManager.createIndexer(IndexedEntity.class).startAndWait();
//
//        QueryBuilder queryBuilder = entityManager
//                .getSearchFactory().buildQueryBuilder()
//                .forEntity(IndexedEntity.class)
//                .get();
//
//        Query query = queryBuilder
//                .keyword()
//                .wildcard()
//                .onField("id")
//                .matching(entity.getId().toString())
//                .createQuery();
//
//        List<IndexedEntity> resultList = entityManager
//                .createFullTextQuery(query, IndexedEntity.class)
//                .getResultList();
//        assertThat(resultList.size(), is(1));
//
//
//    }

}
