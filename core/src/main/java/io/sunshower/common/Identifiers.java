package io.sunshower.common;

/**
 * Created by haswell on 10/17/17.
 */
public class Identifiers {

    /**
     * Used to expose an ID's byte value without copying.  Do not use unless you really
     * know what you're doing
     * @param id
     * @return
     */
    public static byte[] getBytes(Identifier id) {
        return id.id;
    }
    
}
