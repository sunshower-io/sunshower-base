package org.opensky;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by haswell on 4/7/17.
 */
@NameBinding
@Target({
        ElementType.FIELD,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenSky {

}
