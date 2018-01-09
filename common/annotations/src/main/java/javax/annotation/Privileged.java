package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Permission;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Privileged {
  Class<? extends Permission> value();
}
