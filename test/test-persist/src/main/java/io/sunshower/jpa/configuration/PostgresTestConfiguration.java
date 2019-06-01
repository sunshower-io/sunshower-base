package io.sunshower.jpa.configuration;

import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.annotations.Persistence;
import io.sunshower.test.common.TestClasspath;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("postgres")
@DirtiesContext(
  hierarchyMode = DirtiesContext.HierarchyMode.EXHAUSTIVE,
  classMode = DirtiesContext.ClassMode.BEFORE_CLASS
)
@Persistence(id = "test", migrationLocations = "classpath:{dialect}")
public class PostgresTestConfiguration {

  @Bean
  @Primary
  public ConfigurationSource configurationSource() {
    final ConfigFilesProvider provider =
        () -> Collections.singletonList(Paths.get("application-postgres.yml"));

    final FilesConfigurationSource source = new FilesConfigurationSource(provider);
    return source;
  }

  @Bean
  @Primary
  public ConfigurationProvider testConfigurationProvider(ConfigurationSource source) {
    final Path path = TestClasspath.rootDir().resolve("src/test/resources").toAbsolutePath();
    final Environment environment = new ImmutableEnvironment(path.toAbsolutePath().toString());
    return new ConfigurationProviderBuilder()
        .withEnvironment(environment)
        .withConfigurationSource(source)
        .build();
  }

  @Bean
  @Primary
  public Dialect databaseDialect() {
    return Dialect.Postgres;
  }
}
