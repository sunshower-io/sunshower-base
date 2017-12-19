package io.sunshower.jpa.configuration;

import io.sunshower.persistence.annotations.Persistence;
import org.springframework.context.annotation.Configuration;


@Configuration
@Persistence(
        id = "test",
        migrationLocations = "classpath:{dialect}"
)
public class H2TestConfiguration {
}
