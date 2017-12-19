package io.sunshower.common.rs;


import io.sunshower.common.Identifier;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by haswell on 3/17/17.
 */
public class IdentifierConverter extends XmlAdapter<String, Identifier> {

    @Override
    public Identifier unmarshal(String v) throws Exception {
        if(v == null) {
            return null;
        }
        return Identifier.valueOf(v);
    }

    @Override
    public String marshal(Identifier v) throws Exception {
        if(v == null) {
            return null;
        }
        return v.toString();
    }
}
