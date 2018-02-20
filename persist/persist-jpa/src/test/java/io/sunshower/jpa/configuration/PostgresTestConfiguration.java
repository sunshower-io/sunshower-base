package io.sunshower.jpa.configuration;

import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.annotations.Persistence;
import java.nio.file.Paths;
import java.util.Collections;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
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
        () -> {
          try {
            return Collections.singletonList(
                Paths.get(ClassLoader.getSystemResource("application-postgres.yml").toURI()));
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        };
    final FilesConfigurationSource source = new FilesConfigurationSource(provider);
    return source;
  }

  @Bean
  @Primary
  public ConfigurationProvider testConfigurationProvider(ConfigurationSource source) {
    return new ConfigurationProviderBuilder().withConfigurationSource(source).build();
  }

  @Bean
  @Primary
  public Dialect databaseDialect() {
    return Dialect.Postgres;
  }
}
