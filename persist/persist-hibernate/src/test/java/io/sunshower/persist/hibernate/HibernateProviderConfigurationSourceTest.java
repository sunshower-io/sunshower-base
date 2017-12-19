package io.sunshower.persist.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Created by haswell on 5/2/17.
 */
@RunWith(SpringRunner.class)
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
        assertThat(regionFactory, is("io.sunshower.ignite.core.IgniteHibernateRegionFactory"));
    }

    @Test
    public void ensureL2CacheIsEnabled() {
        boolean queryCacheEnabled = props.getProvider().getCache().isEnabled();
        assertTrue(queryCacheEnabled);
    }
}