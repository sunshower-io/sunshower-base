package io.sunshower.test.common;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.git.FirstTokenBranchResolver;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

@ContextConfiguration
public class TestConfigurationConfiguration {

    @Bean
    public ConfigurationSource configurationSource() {
        return new GitConfigurationSourceBuilder()
                .withRepositoryURI(resolve())
                .withBranchResolver(new FirstTokenBranchResolver())
                .withConfigFilesProvider(() -> Arrays.asList(Paths.get("sunshower.yml")))
                .build();
    }


    @Bean
    public ConfigurationProvider testConfigurationProvider(ConfigurationSource source) {
        return new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withEnvironment(
                        new ImmutableEnvironment("/common/common-config/src/test/resources")
                ).build();
    }


    String resolve() {
        File file = new File(ClassLoader.getSystemResource(".").getFile());
        while (file != null) {
            final File repo = new File(file, ".git");
            if (repo.exists()) {
                return repo.getAbsolutePath();
            }
            file = file.getParentFile();
        }
        throw new IllegalStateException("Can't run outside of a repo");
    }

}
