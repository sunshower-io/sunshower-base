package io.sunshower.persist.hibernate.types;

import io.sunshower.common.Identifier;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;


public class FlakeIdentifierType extends AbstractSingleColumnStandardBasicType<Identifier> {


    public static final FlakeIdentifierType INSTANCE = new FlakeIdentifierType();


    public FlakeIdentifierType() {
        super(
                BinaryTypeDescriptor.INSTANCE, 
                FlakeSQLTypeDescriptor.INSTANCE
        );
    }

    @Override
    public String getName() {
        return "flake-binary";
    }
}
