package io.sunshower.jpa.configuration;

import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.annotations.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by haswell on 5/2/17.
 */
@Configuration
@ActiveProfiles("postgres")
@Persistence(
        id = "test",
        migrationLocations = "classpath:{dialect}"
)
public class PostgresTestConfiguration {

    @Bean
    @Primary
    public Dialect databaseDialect() {
        return Dialect.Postgres;
    }
}
