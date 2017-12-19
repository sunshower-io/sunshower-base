package io.sunshower.common.rs;

import org.eclipse.persistence.internal.helper.ConversionManager;
import org.eclipse.persistence.internal.oxm.XMLConversionManager;

import java.util.UUID;

/**
 * Created by haswell on 11/7/16.
 */
public class DefaultConversionManager extends XMLConversionManager {
    private DefaultConversionManager() {
        super();
    }

    static class Holder {
        static final ConversionManager INSTANCE = new DefaultConversionManager();
    }

    public static ConversionManager getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Object convertObject(Object sourceObject, Class javaClass) {
        if(javaClass == UUID.class) {
            if(sourceObject != null) {
                return UUID.fromString((String) sourceObject);
            }
        }
        return super.convertObject(sourceObject, javaClass);
    }
}
