package io.sunshower.persistence.core;

import java.lang.annotation.*;

/**
 * Created by haswell on 2/25/17.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Revisioned {
    int value();
}
