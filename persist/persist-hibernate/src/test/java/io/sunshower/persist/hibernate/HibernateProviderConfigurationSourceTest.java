package io.sunshower.persist.hibernate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;



@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProviderTestConfiguration.class)
public class HibernateProviderConfigurationSourceTest {

    @Inject
    private HibernateProviderConfigurationSource props;

    @Test
    public void ensurePropertiesAreInjected() {
        assertThat(props, is(not(nullValue())));
    }

    @Test
    public void ensureProviderIsNotNull() {
        assertThat(props.getProvider(), is(not(nullValue())));
    }

    @Test
    public void ensureDdlShowSqlExists() {
        assertThat(props.ddl().showSql(), is(true));
    }

    @Test
    public void ensureDdlGenerateExists() {
        assertThat(props.ddl().generate(), is(false));
    }

    @Test
    public void ensureSearchTypeIsCorrect() {
        assertThat(props.getSearch().type(), is("hibernate.getSearch.default.directory_provider"));
    }
    @Test
    public void ensureSearchValueIsCorrect() {
        assertThat(props.getSearch().type(), is("filesystem"));
    }


    @Test
    public void ensureRegionFactoryIsCorrect() {
        String regionFactory = props.cache().regionFactory();
        assertThat(regionFactory, is("org.apache.ignite.cache.hibernate.HibernateRegionFactory"));
    }

    @Test
    public void ensureL2CacheIsEnabled() {
        boolean queryCacheEnabled = props.cache().enabled();
        assertTrue(queryCacheEnabled);
    }
}