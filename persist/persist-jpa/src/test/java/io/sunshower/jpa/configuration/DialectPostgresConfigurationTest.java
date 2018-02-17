package io.sunshower.jpa.configuration;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.core.DatabaseConfigurationSource;
import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.MigrationResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("postgres")
@RunWith(JUnitPlatform.class)
@ContextConfiguration(
  classes = {
    FlywayConfiguration.class,
    DataSourceConfiguration.class,
    PostgresTestConfiguration.class,
  }
)
public class DialectPostgresConfigurationTest {

  @Inject private DataSource dataSource;

  @Inject private MigrationResult result;
  @Inject private Dialect dialect;

  @Inject private DatabaseConfigurationSource source;

  @Test
  public void ensureDialectIsAsExpected() {
    assertThat(dialect, is(Dialect.Postgres));
  }

  @Test
  public void ensureUrlIsExpected() {
    assertThat(source.url(), is("jdbc:arjuna:h2:mem:pg;MODE=PostgreSQL;LOCK_MODE=0;MV_STORE=false;DB_CLOSE_DELAY=-1;"));
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
