package io.sunshower.persistence.search;

import java.lang.annotation.*;

/**
 * Created by haswell on 3/3/17.
 */

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableIndexAware {

}
