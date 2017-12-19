package io.sunshower.ignite;

import org.apache.ignite.Ignite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;


@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(
        classes = {
        IgniteNodeConfiguration.class
})
@SpringBootTest
public class IgniteConfigurationTest {

    @Value("${ignite.fabric-name}")
    private String name;

    @Inject
    private Ignite ignite;

    @Inject
    private IgniteConfigurationSource igniteConfiguration;


    @Test
    public void ensureDiscoveryModeIsCorrect() {
        String mode = igniteConfiguration.getDiscovery().getMode();
        assertThat(mode, is("vm-local"));

    }

    @Test
    public void ensureFabricNameIsCorrect() {
        assertThat(igniteConfiguration.getFabricName(), is("test"));

    }

    @Test
    public void ensureNameIsLoaded() {
        assertThat(name, is("test"));
    }




    @Test
    public void ensureNameIsInjected() {
        assertThat(igniteConfiguration.getFabricName(), is(not(nullValue())));
    }

    @Test
    public void ensureNameIsExpected() {
        assertThat(igniteConfiguration.getFabricName(), is("test"));
    }


    @Test
    public void ensureIgniteCanStartFromProperlyConfiguredNode() {


    }
}