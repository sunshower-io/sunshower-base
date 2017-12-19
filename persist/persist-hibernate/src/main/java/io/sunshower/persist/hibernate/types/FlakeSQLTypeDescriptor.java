package io.sunshower.persist.hibernate.types;

import io.sunshower.common.Identifier;
import io.sunshower.common.Identifiers;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

/**
 * Created by haswell on 10/17/17.
 */
public class FlakeSQLTypeDescriptor extends AbstractTypeDescriptor<Identifier> {

    
    public static final FlakeSQLTypeDescriptor INSTANCE = new FlakeSQLTypeDescriptor();

    public FlakeSQLTypeDescriptor() {
        super(Identifier.class);
    }


    @Override
    public String toString(Identifier value) {
        return FlakeIdentifierTransformer.INSTANCE.transform(value);
    }

    @Override
    public Identifier fromString(String string) {
        return FlakeIdentifierTransformer.INSTANCE.parse(string);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(Identifier value, Class<X> type, WrapperOptions options) {
        
        if(value == null) {
            return null;
        }
       
        if(Identifier.class.isAssignableFrom(type)) {
            return (X) value; 
        }
        if(String.class.isAssignableFrom(type)) {
            return (X) FlakeIdentifierTransformer.INSTANCE.transform(value);
        } 
        if(byte[].class.isAssignableFrom(type)) {
            return (X) Identifiers.getBytes(value);
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Identifier wrap(X value, WrapperOptions options) {
        if(value == null) {
            return null;
        }
        
        if(Identifier.class.isInstance(value)) {
            return (Identifier) value;
        }
        
        if(String.class.isInstance(value)) {
            return Identifier.valueOf((String)value);
        }
        if(byte[].class.isInstance(value)) {
            return Identifier.valueOf((byte[]) value);
        }
        throw unknownUnwrap(value.getClass());
    }
}
