package io.sunshower.ignite;

import io.sunshower.test.common.TestConfigurationConfiguration;
import org.apache.ignite.Ignite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;


@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(
        classes = {
                IgniteNodeConfiguration.class,
                TestConfigurationConfiguration.class
        })
public class IgniteConfigurationTest {


    @Inject
    private Ignite ignite;

    @Inject
    private IgniteConfigurationSource igniteConfiguration;


    @Test
    public void ensureDiscoveryModeIsCorrect() {
        String mode = igniteConfiguration.getDiscovery().mode();
        assertThat(mode, is("vm-local"));
    }

    @Test
    public void ensureFabricNameIsCorrect() {
        assertThat(igniteConfiguration.getFabricName(), is("sunshower-data-fabric"));

    }


    @Test
    public void ensureNameIsInjected() {
        assertThat(igniteConfiguration.getFabricName(), is(not(nullValue())));
    }

    @Test
    public void ensureNameIsExpected() {
        assertThat(igniteConfiguration.getFabricName(), is("sunshower-data-fabric"));
    }


    @Test
    public void ensureIgniteCanStartFromProperlyConfiguredNode() {


    }
}