package io.sunshower.test.ws;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Environment {
    
    String[] properties() default {};
    
    PortType port() default PortType.Random;
    
    Class<?>[] classes() default {};
}
