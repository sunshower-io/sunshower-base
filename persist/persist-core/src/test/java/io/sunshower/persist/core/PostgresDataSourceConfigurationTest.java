package io.sunshower.persist.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


@SpringBootTest
@ActiveProfiles("postgres")
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataSourceConfiguration.class
})

public class PostgresDataSourceConfigurationTest {

    @Inject
    private DataSource dataSource;

    @Inject
    private DatabaseConfigurationSource source;

    @Test
    public void ensureUrlIsExpected() {
        assertThat(source.getUrl(), is("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1"));
    }


    @Test
    public void ensureDataSourceIsInjected() {
        assertThat(dataSource, is(not(nullValue())));
    }

    @Test
    public void ensurePostgresSchemataIsCreatedWithTestEntityPostgresExisting() throws SQLException {
        try(Connection cnx = dataSource.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            preparedStatement.executeQuery();
        }
    }

}
