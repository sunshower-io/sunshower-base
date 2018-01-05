package io.sunshower.persist.hibernate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;



@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
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
        assertThat(props.getProvider().getDdl().isShowSql(), is(true));
    }

    @Test
    public void ensureDdlGenerateExists() {
        assertThat(props.getProvider().getDdl().isGenerate(), is(false));
    }

    @Test
    public void ensureSearchTypeIsCorrect() {
        assertThat(props.getProvider().getSearch().getType(), is("hibernate.search.default.directory_provider"));
    }
    @Test
    public void ensureSearchValueIsCorrect() {
        assertThat(props.getProvider().getSearch().getValue(), is("filesystem"));
    }


    @Test
    public void ensureRegionFactoryIsCorrect() {
        String regionFactory = props.getProvider().getCache().getRegionFactory();
        assertThat(regionFactory, is("org.apache.ignite.cache.hibernate.HibernateRegionFactory"));
    }

    @Test
    public void ensureL2CacheIsEnabled() {
        boolean queryCacheEnabled = props.getProvider().getCache().isEnabled();
        assertTrue(queryCacheEnabled);
    }
}