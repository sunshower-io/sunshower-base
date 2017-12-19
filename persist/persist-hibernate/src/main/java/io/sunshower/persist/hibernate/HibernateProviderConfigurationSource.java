package io.sunshower.persist.hibernate;

import io.sunshower.jpa.configuration.JpaProviderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Created by haswell on 5/2/17.
 */

@ConfigurationProperties("jpa")
public class HibernateProviderConfigurationSource extends JpaProviderProperties {


    private HibernateDialectProperties provider;


    public HibernateDialectProperties getProvider() {
        return provider;
    }

    public void setProvider(HibernateDialectProperties provider) {
        this.provider = provider;
    }

    public Properties toNative() {
        final Properties properties = new Properties();
        configureSearch(properties);

        configureDialect(properties);
        configureDiagnostics(properties);

        return properties;
    }

    private void configureDialect(Properties properties) {
        properties.put("jpa.dialect", provider.getDialect());
    }

    private void configureDiagnostics(Properties properties) {
        DataDefinitionLanguage ddl = provider.getDdl();
        properties.setProperty("hibernate.show_sql", Boolean.toString(ddl.isShowSql()));
        properties.setProperty("hibernate.format_sql", Boolean.toString(ddl.isFormatSql()));
        if(ddl.isGenerate()) {
            properties.setProperty("hibernate.hbm2ddl.auto", ddl.getStrategy());
        }
    }

    private void configureSearch(Properties properties) {
        SearchConfiguration search = provider.getSearch();
        if(search != null) {
            properties.setProperty(search.getType(), search.getValue());
        }
    }
}
