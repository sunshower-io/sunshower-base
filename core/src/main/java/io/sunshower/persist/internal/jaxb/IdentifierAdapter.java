package io.sunshower.persist.internal.jaxb;

import io.sunshower.common.Identifier;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by haswell on 7/24/17.
 */
public class IdentifierAdapter extends XmlAdapter<String, Identifier> {


    @Override
    public Identifier unmarshal(String v) throws Exception {
        if(v != null) {
            return Identifier.valueOf(v);
        }
        return null;
    }

    @Override
    public String marshal(Identifier v) throws Exception {
        if(v == null) {
            return null;
        }
        return v.toString();
    }
}
