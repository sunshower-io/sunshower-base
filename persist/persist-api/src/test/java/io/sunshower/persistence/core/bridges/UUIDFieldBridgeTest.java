package io.sunshower.persistence.core.bridges;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.UUID;
import java.nio.ByteBuffer;


@RunWith(JUnitPlatform.class)
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