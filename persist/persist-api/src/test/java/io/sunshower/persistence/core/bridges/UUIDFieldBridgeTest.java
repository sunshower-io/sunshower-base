package io.sunshower.persistence.core.bridges;

import org.junit.Test;

import java.util.UUID;
import java.nio.ByteBuffer;

/**
 * Created by haswell on 4/12/17.
 */
public class UUIDFieldBridgeTest {


    @Test
    public void ensureUUIDIsAlwaysTheSame() {
        for(int i = 0; i < 100; i++) {
            UUID id = UUID.randomUUID();
            long fst = id.getMostSignificantBits();
            long snd = id.getLeastSignificantBits();
            ByteBuffer b = ByteBuffer.allocate(16);
            b.putLong(fst);
            b.putLong(snd);
        }
    }

}