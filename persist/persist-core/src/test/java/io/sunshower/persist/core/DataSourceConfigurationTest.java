package io.sunshower.persist.core;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Created by haswell on 5/2/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataSourceConfiguration.class)
public class DataSourceConfigurationTest {
    @Inject
    private DataSource dataSource;

    @Inject
    private DatabaseConfigurationSource source;


    @Test
    public void ensureConfigurationIsInjected() {
        assertThat(source, is(not(nullValue())));
    }

    @Test
    public void ensureDataSourceIsInjected() {
        assertThat(dataSource, is(not(nullValue())));
    }

    @Test
    public void ensureDataSourceIsLive() throws SQLException {
        try(Connection cnx = dataSource.getConnection()) {
            cnx.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES")
                    .execute();
        }
    }


}