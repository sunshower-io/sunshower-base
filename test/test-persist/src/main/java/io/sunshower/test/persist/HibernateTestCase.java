package io.sunshower.test.persist;

import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.hibernate.HibernateConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by haswell on 10/17/16.
 */


@Import({
        FlywayConfiguration.class,
        DataSourceConfiguration.class,
        FlywayConfiguration.class,
        HibernateConfiguration.class,
})
@Transactional
@Configuration
@SpringBootTest
public class HibernateTestCase {






}
