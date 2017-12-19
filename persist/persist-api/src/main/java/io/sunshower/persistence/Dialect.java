package io.sunshower.persistence;

/**
 * Created by haswell on 10/17/16.
 */
public enum Dialect {
    H2("h2"),
    Postgres("postgres");

    final String key;
    Dialect(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
