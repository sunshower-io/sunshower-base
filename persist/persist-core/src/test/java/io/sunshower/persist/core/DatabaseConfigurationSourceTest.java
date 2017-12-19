package io.sunshower.persist.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 5/5/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataSourceConfiguration.class)
public class DatabaseConfigurationSourceTest {

    @Inject
    private DatabaseConfigurationSource source;

    @Test
    public void ensureBaselineIsRead() {
        assertThat(source.isBaseline(), is(true));
    }

    @Test
    public void ensureBaselineVersionIsRead() {
        assertThat(source.getVersion(), is("0"));

    }


}