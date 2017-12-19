package io.sunshower.jpa.configuration;

import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persistence.annotations.Persistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.sunshower.jpa.flyway.FlywayConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by haswell on 5/2/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                FlywayConfiguration.class,
                DataSourceConfiguration.class,
                H2TestConfiguration.class
        }
)
@SpringBootTest
public class JpaConfigurationSourceTest {


    @Inject
    private DataSource dataSource;



    @Test
    @SuppressWarnings("all")
    public void ensurePostgresSchemataIsCreatedWithNoH2EntityExisting() throws SQLException {
        try(Connection cnx = dataSource.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'H2_ENTITY'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while(resultSet.next()) {
                count++;
            }
            assertThat(count, is(1));
        }
    }

}