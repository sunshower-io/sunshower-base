package io.sunshower.jpa.configuration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.core.DatabaseConfigurationSource;
import io.sunshower.persistence.MigrationResult;
import io.sunshower.test.common.TestConfigurationConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("postgres")
@RunWith(JUnitPlatform.class)
@ContextConfiguration(
  classes = {
    FlywayConfiguration.class,
    DataSourceConfiguration.class,
    PostgresTestConfiguration.class,
    TestConfigurationConfiguration.class
  }
)
public class DialectPostgresConfigurationTest {

  @Inject private DataSource dataSource;

  @Inject private MigrationResult result;

  @Inject private DatabaseConfigurationSource source;

  @Test
  public void ensureUrlIsExpected() {
    assertThat(source.url(), is("jdbc:h2:mem:;MODE=PostgreSQL;LOCK_MODE=0;MV_STORE=false;"));
  }

  @Test
  public void ensureDataSourceIsInjected() {
    assertThat(dataSource, is(not(nullValue())));
  }

  @Test
  public void ensurePostgresSchemataIsCreatedWithNoH2EntityExisting() throws SQLException {
    try (Connection cnx = dataSource.getConnection()) {
      PreparedStatement preparedStatement =
          cnx.prepareStatement(
              "SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'H2_ENTITY'");
      ResultSet resultSet = preparedStatement.executeQuery();
      int count = 0;
      while (resultSet.next()) {
        count++;
      }
      assertThat(count, is(0));
    }
  }

  @Test
  public void ensurePostgresSchemataIsCreatedWithTestEntityPostgresExisting() throws SQLException {
    try (Connection cnx = dataSource.getConnection()) {
      PreparedStatement preparedStatement =
          cnx.prepareStatement(
              "SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'POSTGRES_ENTITY'");
      ResultSet resultSet = preparedStatement.executeQuery();
      int count = 0;
      while (resultSet.next()) {
        count++;
      }
      assertThat(count, is(1));
    }
  }
}
