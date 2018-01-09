package io.sunshower.test.ws;

import java.lang.annotation.*;
import org.springframework.test.context.BootstrapWith;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@BootstrapWith(RESTBootstrapper.class)
public @interface EnableJAXRS {
  int port() default -1;

  String location() default "127.0.0.1";
}
