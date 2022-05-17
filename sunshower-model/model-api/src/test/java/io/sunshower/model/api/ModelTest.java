package io.sunshower.model.api;

import io.sunshower.arcus.persist.flyway.FlywayTestConfiguration;
import jakarta.transaction.Transactional;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlywayTestConfiguration.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelTest {

}
