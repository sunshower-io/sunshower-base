package io.sunshower.persist.hibernate.types;

import io.sunshower.common.Identifier;

/**
 * Created by haswell on 10/17/17.
 */
public class FlakeIdentifierTransformer {
    
    public static final FlakeIdentifierTransformer 
            INSTANCE = new FlakeIdentifierTransformer();
    
    public String transform(Identifier value) {
        return value.toString();
    }
    
    public Identifier parse(String value) {
        return Identifier.valueOf(value);
    }
}
