package io.sunshower.persist.core;

import static io.sunshower.persist.core.DataSourceConfigurations.toNative;
import static io.sunshower.persist.core.DataSourceConfigurations.useLocation;

import com.zaxxer.hikari.HikariDataSource;
import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.UnsupportedDatabaseException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.cfg4j.provider.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

  static final Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);

  @Bean
  @Singleton
  public Dialect databaseDialect(DataSource dataSource) throws SQLException {
    try (final Connection cnx = dataSource.getConnection()) {
      final String name = cnx.getMetaData().getDatabaseProductName().toLowerCase();
      switch (name) {
        case "h2":
          log.info("dialect is 'h2'");
          return Dialect.H2;
        case "postgresql":
          log.info("dialect is 'postgres'");
          return Dialect.Postgres;
      }
      throw new UnsupportedDatabaseException("Sunshower does not support the database: " + name);
    }
  }

  @Bean
  public DatabaseConfigurationSource databaseConfigurationSource(ConfigurationProvider provider) {
    return provider.bind("jdbc", DatabaseConfigurationSource.class);
  }

  @Bean
  public DataSource dataSource(DatabaseConfigurationSource cfg) throws NamingException {
    if (useLocation(cfg)) {
      log.info("Starting JDBC data-source...");
      final DataSource result = new HikariDataSource(toNative(cfg));
      log.info("Successfully started data-source");
      return result;
    } else {
      log.info("Starting JNDI data-source...");
      final DataSource result = InitialContext.doLookup(cfg.jndiPath());
      log.info("Successfully started data-source");
      return result;
    }
  }
}
