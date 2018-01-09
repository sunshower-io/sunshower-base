package io.sunshower.persist.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.sunshower.test.common.TestConfigurationConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@ActiveProfiles("postgres")
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
  classes = {DataSourceConfiguration.class, TestConfigurationConfiguration.class}
)
public class PostgresDataSourceConfigurationTest {

  @Inject private DataSource dataSource;

  @Inject private DatabaseConfigurationSource source;

  @Test
  public void ensureUrlIsExpected() {
    assertThat(source.url(), is("jdbc:h2:mem:frap;MODE=PostgreSQL;LOCK_MODE=0;MV_STORE=false;"));
  }

  @Test
  public void ensureDataSourceIsInjected() {
    assertThat(dataSource, is(not(nullValue())));
  }

  @Test
  public void ensurePostgresSchemataIsCreatedWithTestEntityPostgresExisting() throws SQLException {
    try (Connection cnx = dataSource.getConnection()) {
      PreparedStatement preparedStatement =
          cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES");
      preparedStatement.executeQuery();
    }
  }
}
