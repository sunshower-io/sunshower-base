package io.sunshower.persist.pg;

import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.annotations.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Persistence(
        id = "frap",
        scannedPackages = "io.sunshower.persist.pg",
        migrationLocations = "classpath:{dialect}"
)
public class PgCfg {

    @Bean
    public Dialect databaseDialect() {
        return Dialect.Postgres;
    }
}
