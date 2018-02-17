package io.sunshower.jpa.configuration;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.core.DatabaseConfigurationSource;
import io.sunshower.persistence.Dialect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitPlatform.class)
@ContextConfiguration(
  classes = {FlywayConfiguration.class, DataSourceConfiguration.class, H2TestConfiguration.class}
)
@ExtendWith(SpringExtension.class)
public class JpaConfigurationSourceTest {

  @Inject private DataSource dataSource;

  @Inject private Dialect dialect;
  @Inject private DatabaseConfigurationSource source;

  @Test
  public void ensureDialectIsAsExpected() {
    assertThat(dialect, is(Dialect.H2));
  }

  @Test
  public void ensureUrlIsAsExpected() {
    assertThat(
        source.url(), is("jdbc:arjuna:h2:mem:h2;LOCK_MODE=0;MV_STORE=false;DB_CLOSE_DELAY=-1;"));
  }

  @Test
  @SuppressWarnings("all")
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
      assertThat(count, is(1));
    }
  }
}
