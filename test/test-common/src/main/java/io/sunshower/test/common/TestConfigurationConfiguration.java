package io.sunshower.test.common;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.git.FirstTokenBranchResolver;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Paths;
import java.util.Arrays;

@ContextConfiguration
public class TestConfigurationConfiguration {


    @Bean
    public ConfigurationProvider testConfigurationProvider() {
        ConfigurationSource source = new GitConfigurationSourceBuilder()
                .withRepositoryURI("https://github.com/sunshower-io/sunshower-base.git")
                .withBranchResolver(new FirstTokenBranchResolver())
                .withConfigFilesProvider(() -> Arrays.asList(Paths.get("sunshower.yml")))
                .build();
        return new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withEnvironment(
                        new ImmutableEnvironment("/common/common-config/src/test/resources")
                ).build();
    }

}
