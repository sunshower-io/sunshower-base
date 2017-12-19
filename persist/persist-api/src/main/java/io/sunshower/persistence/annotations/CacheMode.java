package io.sunshower.persistence.annotations;

import java.lang.annotation.*;

/**
 * Created by haswell on 2/20/17.
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheMode {

    Mode value() default Mode.Local;

    enum Mode {
        Local,
        None,
        Grid,
        Container
    }
}
