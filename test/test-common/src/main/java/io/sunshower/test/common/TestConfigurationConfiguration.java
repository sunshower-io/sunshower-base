package io.sunshower.test.common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.inject.Named;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TestConfigurationConfiguration {

  @Bean
  public ConfigurationSource configurationSource() {
    return new FilesConfigurationSource(() -> Arrays.asList(Paths.get("sunshower.yml")));
  }

  @Bean
  public ConfigurationProvider testConfigurationProvider(
      ConfigurationSource source,
      @Named(TestConfigurations.TEST_CONFIGURATION_REPOSITORY_PATH) String location) {
    return new ConfigurationProviderBuilder()
        .withConfigurationSource(source)
        .withEnvironment(new ImmutableEnvironment(location))
        .build();
  }

  @Bean(name = TestConfigurations.TEST_CONFIGURATION_REPOSITORY_PATH)
  public String testConfigurationLocation() {
    return TestClasspath.rootDir()
        .getParent()
        .getParent()
        .resolve("common/common-config/src/test/resources")
        .toFile()
        .getAbsolutePath();
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
