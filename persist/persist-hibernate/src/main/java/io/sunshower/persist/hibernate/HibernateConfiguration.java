package io.sunshower.persist.hibernate;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple;
import io.sunshower.ignite.IgniteNodeConfiguration;
import io.sunshower.persist.validation.ModelValidator;
import io.sunshower.persistence.PersistenceUnit;
import java.util.Properties;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.ignite.Ignite;
import org.cfg4j.provider.ConfigurationProvider;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
@Import(IgniteNodeConfiguration.class)
public class HibernateConfiguration {

  static final Logger log = Logger.getLogger(HibernateConfiguration.class.getName());

  @Bean
  public HibernateProviderConfigurationSource hibernateProviderConfigurationSource(
      DataDefinitionLanguage ddl,
      SearchConfiguration searchConfiguration,
      HibernateDialectProperties props,
      HibernateCacheConfiguration cacheConfiguration) {
    return new HibernateProviderConfigurationSource(
        ddl, searchConfiguration, props, cacheConfiguration);
  }

  @Bean
  public DataDefinitionLanguage dataDefinitionLanguage(ConfigurationProvider source) {
    return source.bind("jpa.provider.ddl", DataDefinitionLanguage.class);
  }

  @Bean
  public HibernateCacheConfiguration hibernateCacheConfiguration(ConfigurationProvider source) {
    return source.bind("jpa.provider.cache", HibernateCacheConfiguration.class);
  }

  @Bean
  public SearchConfiguration searchConfiguration(ConfigurationProvider source) {
    return source.bind("jpa.provider.search", SearchConfiguration.class);
  }

  @Bean
  public HibernateDialectProperties hibernateDialectProperties(ConfigurationProvider source) {
    return source.bind("jpa.provider", HibernateDialectProperties.class);
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      EntityManagerFactory entityManagerFactory, DataSource dataSource) {
    JtaTransactionManager mgr = new JtaTransactionManager();
    mgr.setUserTransaction(new UserTransactionImple());
    mgr.setTransactionManager(new TransactionManagerImple());
    return mgr;

    //    JpaTransactionManager transactionManager = new JpaTransactionManager();
    //    transactionManager.setDataSource(dataSource);
    //    transactionManager.setEntityManagerFactory(entityManagerFactory);
    //    return transactionManager;
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
      ConfigurationProvider provider,
      Ignite ignite) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setJtaDataSource(dataSource);
    entityManagerFactoryBean.setPersistenceUnitName("default-persistence-unit");
    //    entityManagerFactoryBean.setDataSource(dataSource);
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

    entityManagerFactoryBean.setPackagesToScan(persistenceConfiguration.getScannedPackages());

    Properties properties = Configurations.toNative(provider, source);
    configureCache(properties, provider, source.getCache(), source);
    entityManagerFactoryBean.setJpaProperties(properties);
    return entityManagerFactoryBean;
  }

  private void configureCache(
      Properties jpaProperties,
      ConfigurationProvider cfgProvider,
      HibernateCacheConfiguration cache,
      HibernateProviderConfigurationSource source) {
    HibernateDialectProperties provider = Configurations.getProvider(cfgProvider);
    if (provider == null) {
      log.info("No L2 Cache configured");
      return;
    }

    if (cache == null) {
      log.info("No L2 Cache configured");
      return;
    }

    if (!cache.enabled()) {
      log.info("L2 Cache is disabled");
      return;
    }

    String cacheProvider = cache.provider();
    log.info("L2 Cache is enabled");
    log.info("Cache provider: '" + cacheProvider + "'");

    jpaProperties.put("hibernate.cache.use_second_level_cache", true);

    if (cache.enableQueryCache()) {
      log.info("Query cache is enabled");
      jpaProperties.put("hibernate.cache.use_query_cache", true);
    }

    jpaProperties.put("hibernate.cache.region.factory_class", cache.regionFactory());
    jpaProperties.put("hibernate.default_schema", "SUNSHOWER");

    jpaProperties.put("org.apache.ignite.hibernate.grid_name", cache.fabricName());
  }

  @Bean
  public FullTextEntityManager entityManager(EntityManagerFactory entityManagerFactory) {
    return Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
  }
}
