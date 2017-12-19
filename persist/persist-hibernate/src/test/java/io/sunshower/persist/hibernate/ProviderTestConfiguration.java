package io.sunshower.persist.hibernate;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by haswell on 5/2/17.
 */
@EnableConfigurationProperties({
        HibernateProviderConfigurationSource.class
})
public class ProviderTestConfiguration {
}
