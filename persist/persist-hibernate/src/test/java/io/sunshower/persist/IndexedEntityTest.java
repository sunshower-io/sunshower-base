package io.sunshower.persist;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import test.entities.IndexedEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@Transactional
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
public class IndexedEntityTest extends HibernateTestCase {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private IndexedEntityService entityService;

    private QueryBuilder          queryBuilder;
    private FullTextEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = Search.getFullTextEntityManager(em);

        queryBuilder = entityManager
                .getSearchFactory().buildQueryBuilder()
                .forEntity(IndexedEntity.class)
                .get();
    }

    @SuppressWarnings("unchecked")
    List<IndexedEntity> query(Query query) {
        return entityManager
                .createFullTextQuery(query, IndexedEntity.class)
                .getResultList();
    }


    @AfterEach
    public void removeEntities() throws IOException {
        entityManager.purgeAll(IndexedEntity.class);
        entityManager.flushToIndexes();
    }

    @Test
    public void ensureServiceIsInjected() {
        assertThat(entityService, is(notNullValue()));

    }

    @Test
    public void ensureEntityManagerIsInjected() {
        assertThat(entityService.getFtEntityManager(), is(not(nullValue())));
    }

    @Test
    public void a_ensureEntityManagerIsSearchable() {
        IndexedEntity entity = new IndexedEntity();
        entity.setName("testalotofthingsthisiscool");
        save(entity);
    }

    @Test
    public void ensureEntityManagerIsSearchable() throws InterruptedException {

        QueryBuilder queryBuilder = entityManager
                .getSearchFactory().buildQueryBuilder()
                .forEntity(IndexedEntity.class)
                .get();
        IndexedEntity entity = new IndexedEntity();
        entity.setName("beanlist");
        save(entity);

        Query query = queryBuilder.keyword()
                .wildcard()
                .onFields("name")
                .matching("beanlist").createQuery();

        List<IndexedEntity> resultList = entityManager
                .createFullTextQuery(query, IndexedEntity.class)
                .getResultList();
        assertThat(resultList.size(), is(1));
    }


    @Test
    public void ensureEntityManagerIsFuzzySearchableBy() throws InterruptedException {

        IndexedEntity entity = new IndexedEntity();
        entity.setName("beanlist");

        save(entity);

        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField("name")
                .matching("bean*st")
                .createQuery();
        assertThat(query(query).size(), is(1));

    }

    private void save(IndexedEntity entity) {
        em.persist(entity);
        em.flush();
        entityManager.flushToIndexes();
    }

    @Test
    public void ensureEntityManagerIsSearchableById() throws InterruptedException {

        IndexedEntity entity = new IndexedEntity();
        entity.setName("beanlist");
        save(entity);


        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField("id")
                .matching(entity.getId().toString())
                .createQuery();

        assertThat(query(query).size(), is(1));
    }

}
