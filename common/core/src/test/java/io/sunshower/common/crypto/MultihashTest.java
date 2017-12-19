package io.sunshower.common.crypto;

import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
public class MultihashTest extends SerializationTestCase {

    public MultihashTest() {
        super(SerializationAware.Format.JSON, Multihash.class);
    }

    @Test
    public void ensureDocumentLooksCorrect() {
        System.out.println(this.write(new Hashes.SHA256().compute("fuckwidget".getBytes())));
    }

}