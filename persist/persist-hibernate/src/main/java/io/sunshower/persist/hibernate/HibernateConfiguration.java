package io.sunshower.persist.hibernate;

import io.sunshower.common.configuration.ConfigurationSource;
import io.sunshower.ignite.IgniteNodeConfiguration;
import io.sunshower.persist.validation.ModelValidator;
import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.PersistenceUnit;
import io.sunshower.persistence.annotations.CacheMode;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by haswell on 10/16/16.
 */
@Configuration
@EnableTransactionManagement
@Import(IgniteNodeConfiguration.class)
@EnableConfigurationProperties(HibernateProviderConfigurationSource.class)
public class HibernateConfiguration {

    static final Logger log = Logger.getLogger(HibernateConfiguration.class.getName());





    @Bean
    public JpaTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory,
            DataSource dataSource
    ) {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    public ModelValidator modelValidator() {
        return new ModelValidator();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            HibernateProviderConfigurationSource source,
            PersistenceUnit persistenceConfiguration,
            Ignite ignite
    ) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("default-persistence-unit");
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());


        entityManagerFactoryBean.setPackagesToScan(
                persistenceConfiguration
                        .getScannedPackages());

        Properties properties = source.toNative();
        configureCache(properties, source);
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }


    private void configureCache(
            Properties jpaProperties,
            HibernateProviderConfigurationSource source
    ) {
        HibernateDialectProperties provider = source.getProvider();
        if(provider == null) {
            log.info("No L2 Cache configured");
            return;
        }

        HibernateCacheConfiguration cache = provider.getCache();
        if(cache == null) {
            log.info("No L2 Cache configured");
            return;
        }

        if(!cache.isEnabled()) {
            log.info("L2 Cache is disabled");
            return;
        }

        String cacheProvider = cache.getProvider();
        log.info("L2 Cache is enabled");
        log.info("Cache provider: '" + cacheProvider + "'");

        jpaProperties.put("hibernate.cache.use_second_level_cache", true);

        if(cache.isQueryCacheEnabled()) {
            log.info("Query cache is enabled");
            jpaProperties.put("hibernate.cache.use_query_cache", true);
        }

        jpaProperties.put("hibernate.cache.region.factory_class", cache.getRegionFactory());



        jpaProperties.put(
                "org.apache.ignite.hibernate.grid_name",
                cache.getFabricName()

        );



//        if (mode != CacheMode.Mode.None) {
//            log.info("Cache mode set to: " + mode);
//            jpaProperties.put(
//                    "hibernate.cache.use_second_level_cache",
//                    true
//            );
//
//            jpaProperties.put(
//                    "hibernate.cache.use_query_cache",
//                    true
//            );
//
//            if (mode == CacheMode.Mode.Local) {
//                jpaProperties.put(
//                        "hibernate.cache.region.factory_class",
//                        "io.sunshower.persist.infinispan.LocalInfinispanCacheManagerFactory"
//                );
//            } else {
//                jpaProperties.put(
//                        "hibernate.cache.region.factory_class",
//                        "org.jboss.as.jpa.hibernate5.infinispan.InfinispanRegionFactory"
//                );
//                jpaProperties.put(
//                        "hibernate.cache.infinispan.cachemanager",
//                        "java:jboss/infinispan/container/hibernate"
//                );
//
//                jpaProperties.put(
//                        "hibernate.transaction.transaction_manager_lookup_class",
//                        "org.hibernate.transaction.JBossTransactionManagerLookup"
//                );
//            }
//        }
    }


    @Bean
    public FullTextEntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    }

}
