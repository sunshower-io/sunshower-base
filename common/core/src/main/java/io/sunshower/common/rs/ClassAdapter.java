package io.sunshower.common.rs;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class ClassAdapter extends XmlAdapter<String, Class<?>> {
    @Override
    public Class<?> unmarshal(String v) throws Exception {
        return Class.forName(v, true, this.getClass().getClassLoader());
    }

    @Override
    public String marshal(Class<?> v) throws Exception {
        return v.getName();
    }
}