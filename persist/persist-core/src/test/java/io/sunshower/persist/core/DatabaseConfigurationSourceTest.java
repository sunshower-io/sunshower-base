package io.sunshower.persist.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@SpringBootTest
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
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