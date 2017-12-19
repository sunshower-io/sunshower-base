package io.sunshower.jpa.configuration;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.core.DatabaseConfigurationSource;
import io.sunshower.persistence.MigrationResult;
import io.sunshower.persistence.annotations.Persistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by haswell on 5/2/17.
 */
@SpringBootTest
@ActiveProfiles("postgres")
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                FlywayConfiguration.class,
                DataSourceConfiguration.class,
                PostgresTestConfiguration.class
        }
)
public class DialectPostgresConfigurationTest {

    @Inject
    private DataSource dataSource;

    @Inject
    private MigrationResult result;

    @Inject
    private DatabaseConfigurationSource source;

    @Test
    public void ensureUrlIsExpected() {
        assertThat(source.getUrl(), is("jdbc:h2:mem:frapper;MODE=PostgreSQL;DB_CLOSE_DELAY=-1"));
    }


    @Test
    public void ensureDataSourceIsInjected() {
        assertThat(dataSource, is(not(nullValue())));
    }


    @Test
    public void ensurePostgresSchemataIsCreatedWithNoH2EntityExisting() throws SQLException {
        try(Connection cnx = dataSource.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'H2_ENTITY'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while(resultSet.next()) {
                count++;
            }
            assertThat(count, is(0));
        }
    }

    @Test
    public void ensurePostgresSchemataIsCreatedWithTestEntityPostgresExisting() throws SQLException {
        try(Connection cnx = dataSource.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'POSTGRES_ENTITY'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while(resultSet.next()) {
                count++;
            }
            assertThat(count, is(1));
        }
    }


}
