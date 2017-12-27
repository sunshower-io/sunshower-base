package io.sunshower.test.ws;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.BootstrapWith;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RemoteExtension.class)
public @interface RESTTest {
}
