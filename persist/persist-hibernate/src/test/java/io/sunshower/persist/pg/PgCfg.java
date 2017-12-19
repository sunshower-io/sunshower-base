package io.sunshower.persist.pg;

import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.annotations.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by haswell on 5/3/17.
 */
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
