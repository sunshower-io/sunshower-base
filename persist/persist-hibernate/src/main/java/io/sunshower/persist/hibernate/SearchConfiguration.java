package io.sunshower.persist.hibernate;

/**
 * Created by haswell on 5/2/17.
 */
public class SearchConfiguration {

    private String type;

    private String value;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
