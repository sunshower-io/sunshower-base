package io.sunshower.common.configuration;

import io.sunshower.core.api.Startable;
import io.sunshower.core.api.Stoppable;


public interface ConfigurationSource extends Startable, Stoppable {

    default String sayHello() {
        return "cool";
    }

    String get(ValueHolder v, boolean fail);

    String get(String key, boolean fail);

    String get(String key);

    String get(String key, String defaultValue);

    String set(String key, String value);

    String set(String key, String value, boolean override);

    boolean isSecure();

    <T> T unwrap(Class<T> type);

}
