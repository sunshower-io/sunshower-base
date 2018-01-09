package io.sunshower.jpa.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RunWith(JUnitPlatform.class)
@ContextConfiguration(
  classes = {
    FlywayConfiguration.class,
    DataSourceConfiguration.class,
    H2TestConfiguration.class,
    TestConfigurationConfiguration.class
  }
)
@DirtiesContext
@ExtendWith(SpringExtension.class)
public class JpaConfigurationSourceTest {

  @Inject private DataSource dataSource;

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
