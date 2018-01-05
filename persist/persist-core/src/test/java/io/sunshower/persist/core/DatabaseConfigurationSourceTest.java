package io.sunshower.persist.core;

import io.sunshower.test.common.TestConfigurationConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataSourceConfiguration.class,
        TestConfigurationConfiguration.class
})
public class DatabaseConfigurationSourceTest {

    @Inject
    private DatabaseConfigurationSource source;

    @Test
    public void ensureBaselineIsRead() {
        assertThat(source.isBaseline(), is(true));
    }

    @Test
    public void ensureBaselineVersionIsRead() {
        assertThat(source.version(), is("0"));
    }

    @Test
    public void ensureValidationWorks() {
        source.validate();
    }


}