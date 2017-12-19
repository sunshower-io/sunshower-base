package io.sunshower.common.configuration;


import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by haswell on 10/16/16.
 */
public class MapConfigurationSource implements ConfigurationSource {

    final Map<String, String> source;

    public MapConfigurationSource(Map<String, String> source) {
        this.source = source;

    }


    @Override
    public void stop() throws Exception {

    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public String get(ValueHolder v, boolean fail) {
        return get(v.value(), fail);
    }

    @Override
    public String get(String key, boolean fail) {
        final String result = source.get(key);
        if(result != null) {
            return result;
        }
        if(fail) {
            throw new NoSuchElementException(createMessage(key));
        }
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String get(String key, String defaultValue) {
        final String result = this.source.get(key);
        if(result == null) {
            return defaultValue;
        }
        return result;
    }

    @Override
    public String set(String key, String value) {
        return set(key, value, false);
    }

    @Override
    public String set(String key, String value, boolean override) {
        final String v = source.get(key);
        if(v == null || override) {
            source.put(key, value);
            return value;
        }
        return null;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> type) {
        return (T) source;
    }

    private String createMessage(String key) {
        final StringBuilder b = new StringBuilder();

        b.append("Failed to find value for key '").append(key).append('\'');
        b.append(" Known pairs: ");
        b.append(source.toString());
        return b.toString();
    }
}
