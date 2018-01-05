package io.sunshower.common.config;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.git.FirstTokenBranchResolver;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SunshowerConfigurationTest {

    ConfigurationSource   source;
    ConfigurationProvider provider;

    @BeforeAll
    public void setUp() {
        source = new GitConfigurationSourceBuilder()
                .withRepositoryURI("https://github.com/sunshower-io/sunshower-base.git")
                .withBranchResolver(new FirstTokenBranchResolver())
                .withConfigFilesProvider(() -> Arrays.asList(Paths.get("sunshower.yml")))
                .build();
        provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withEnvironment(
                        new ImmutableEnvironment("/common/common-config/src/test/resources")
                ).build();
    }

    @Test
    public void ensureRetrievingTestConfigurationWorks() {
        assertThat(source, is(not(nullValue())));
    }


    @Test
    public void ensureRetrievingTestEnvironmentWorks() {
        SampleJdbc jdbc = provider.bind("jdbc", SampleJdbc.class);
        assertThat(jdbc.username(), is("sa"));

    }

    
    public interface SampleJdbc {
        
        String username();

    }

}