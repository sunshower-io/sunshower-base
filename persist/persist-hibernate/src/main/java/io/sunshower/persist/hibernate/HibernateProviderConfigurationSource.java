package io.sunshower.persist.hibernate;

import io.sunshower.jpa.configuration.JpaProviderProperties;

import java.util.Properties;



public class HibernateProviderConfigurationSource implements  JpaProviderProperties {


    private final SearchConfiguration search;
    private final HibernateCacheConfiguration cache;
    private final HibernateDialectProperties provider;
    
    public HibernateProviderConfigurationSource(
            final SearchConfiguration search,
            final HibernateDialectProperties provider,
            final HibernateCacheConfiguration cache
    ) {
        this.cache = cache;
        this.search = search;
        this.provider = provider;
    }


    public HibernateCacheConfiguration cache() {
        return cache;
    }

    public HibernateDialectProperties getProvider() {
        return provider;
    }


    public Properties toNative() {
        final Properties properties = new Properties();
        configureSearch(properties);

        configureDialect(properties);
        configureDiagnostics(properties);

        return properties;
    }

    private void configureDialect(Properties properties) {
        properties.put("jpa.dialect", provider.dialect());
    }

    private void configureDiagnostics(Properties properties) {
        DataDefinitionLanguage ddl = provider.ddl();
        properties.setProperty("hibernate.show_sql", Boolean.toString(ddl.isShowSql()));
        properties.setProperty("hibernate.format_sql", Boolean.toString(ddl.isFormatSql()));
        if(ddl.isGenerate()) {
            properties.setProperty("hibernate.hbm2ddl.auto", ddl.getStrategy());
        }
    }

    private void configureSearch(Properties properties) {
        if(search != null) {
            properties.setProperty(search.type(), search.value());
        }
    }
}
