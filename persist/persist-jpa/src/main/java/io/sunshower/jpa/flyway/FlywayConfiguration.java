package io.sunshower.jpa.flyway;

import io.sunshower.persist.core.DataSourceConfigurations;
import io.sunshower.persist.core.DatabaseConfigurationSource;
import io.sunshower.persistence.Dialect;
import io.sunshower.persistence.MigrationResult;
import io.sunshower.persistence.PersistenceConfiguration;
import io.sunshower.persistence.PersistenceUnit;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Singleton;
import javax.sql.DataSource;

import static io.sunshower.persist.core.DataSourceConfigurations.isBaselineVersion;


@Configuration
public class FlywayConfiguration {
    static final Logger log = LoggerFactory.getLogger(FlywayConfiguration.class);

    @Bean
    public static PersistenceUnit persistenceConfigurationProcessor(ApplicationContext context, Dialect dialect) {
        log.info("Configured with dialect: {}", dialect.getKey());
        return new PersistenceUnit(dialect, context);
    }

    @Bean
    @Singleton
    public MigrationResult createMigrations(
            DataSource dataSource,
            PersistenceUnit context,
            DatabaseConfigurationSource source
    ) {
        for(PersistenceConfiguration ctx : context.configurations()) {
            final Flyway             flyway = new Flyway();

            if(isBaselineVersion(source)) {
                log.info("Setting baseline version to {}", source.version());
                flyway.setBaselineVersionAsString(source.version());
            }
            final String table = ctx.getId() + "_migrations";
            flyway.setTable(table);
            flyway.setDataSource(dataSource);
            String[] migrationPaths = ctx.getMigrationPaths();
            flyway.setLocations(migrationPaths);
            if(isBaselineVersion(source)) {
                log.info("baselining database...");
                flyway.baseline();
                log.info("database baselined");
            }
            flyway.migrate();
        }
        return new MigrationResult(context);
    }

}
