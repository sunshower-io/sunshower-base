package io.sunshower.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by haswell on 11/16/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Persistence {

    String id() default "";

    String[] scannedPackages() default {};

    Class<?>[] entities() default {};

    String[] migrationLocations() default {};

}
