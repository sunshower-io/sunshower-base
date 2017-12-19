package io.sunshower.persistence.core;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Identifiers;
import io.sunshower.persist.Sequence;

/**
 * Created by haswell on 10/18/17.
 */
public class Entities {
    
    static final Sequence<Identifier> 
            defaultFlakeSequence = Identifiers.newSequence(true);
    
    public static Sequence<Identifier> getDefaultFlakeSequence() {
        return defaultFlakeSequence;
    }
}
