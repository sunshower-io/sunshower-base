package io.sunshower.test.persist;

import io.sunshower.persistence.annotations.Persistence;
import io.sunshower.test.common.TestConfigurationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import persist.test.TestService;
import persist.test.TestServiceImpl;



@Configuration
@Import({
        HibernateTestCase.class,
        TestConfigurationConfiguration.class
})
@Persistence(
        id = "audit",
        scannedPackages = "persist.test",
        migrationLocations = "classpath:h2"
)

public class TestConfiguration {

    @Bean
    public TestService testService() {
        return new TestServiceImpl();
    }

    

}
