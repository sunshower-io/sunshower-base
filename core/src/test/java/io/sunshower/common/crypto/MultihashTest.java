package io.sunshower.common.crypto;

import io.sunshower.barometer.jaxrs.SerializationAware;
import io.sunshower.barometer.jaxrs.SerializationTestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by haswell on 1/27/17.
 */
public class MultihashTest extends SerializationTestCase {

    public MultihashTest() {
        super(SerializationAware.Format.JSON, Multihash.class);
    }

    @Test
    public void ensureDocumentLooksCorrect() {
        System.out.println(this.write(new Hashes.SHA256().compute("fuckwidget".getBytes())));


    }

}