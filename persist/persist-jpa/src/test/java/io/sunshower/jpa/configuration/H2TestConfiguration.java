package io.sunshower.jpa.configuration;

import io.sunshower.persistence.annotations.Persistence;
import org.springframework.context.annotation.Configuration;

/**
 * Created by haswell on 5/2/17.
 */
@Configuration
@Persistence(
        id = "test",
        migrationLocations = "classpath:{dialect}"
)
public class H2TestConfiguration {
}
