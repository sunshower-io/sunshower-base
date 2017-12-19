package io.sunshower.persist.hibernate;

import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties({
        HibernateProviderConfigurationSource.class
})
public class ProviderTestConfiguration {
}
