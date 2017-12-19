package io.sunshower.persist.core;

import com.zaxxer.hikari.HikariDataSource;
import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.UnsupportedDatabaseException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by haswell on 10/16/16.
 */
@Configuration
@EnableConfigurationProperties(DatabaseConfigurationSource.class)
public class DataSourceConfiguration {

    static final Logger log = Logger.getLogger(DataSourceConfiguration.class.getName());
    @Bean
    @Singleton
    public Dialect databaseDialect(DataSource dataSource) throws SQLException {
        try (final Connection cnx = dataSource.getConnection()){
            final String name = cnx.getMetaData().getDatabaseProductName().toLowerCase();
            switch(name) {
                case "h2":
                    log.info("dialect is 'h2'");
                    return Dialect.H2;
                case "postgresql":
                    log.info("dialect is 'postgres'");
                    return Dialect.Postgres;
            }
            throw new UnsupportedDatabaseException("Hasli does not support the database: " + name);
        }
    }


    @Bean
    public DataSource dataSource(DatabaseConfigurationSource cfg) {
        log.info("Starting JDBC data-source...");
        final DataSource result = new HikariDataSource(cfg.toNative());
        log.info("Successfully started data-source");
        return result;
    }

}
