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
@BootstrapWith(RESTBootstrapper.class)
public @interface EnableJAXRS {
    int port() default -1;
    String location() default "127.0.0.1";
}
