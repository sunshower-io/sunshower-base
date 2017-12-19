package io.sunshower.persist;


import io.sunshower.common.Identifier;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Clock;
import java.util.Objects;


final class IDSequence implements Sequence<Identifier>, NodeAware, TimeBased {


    
    static final int ID_SIZE = 16;


    
    static final int NODE_SIZE = 6;


    
    static final int SEQUENCE_MAXIMUM = 0xFFFF;


    
    final Object sequenceLock = new Object();

    

    final Clock clock;

    
    final InetAddress node;


    
    final InetAddress nodeIdentity;

    final byte[] seed;

    private volatile int sequence;

    private volatile long currentTime;

    private volatile long previousTime;

    private final boolean applyBackpressure;


    public IDSequence(
            Clock clock,
            byte[] seed,
            InetAddress node,
            boolean applyBackpressure
    ) {
        check(clock, node);
        this.node = node;
        this.clock = clock;
        this.seed = seed;
        this.nodeIdentity = node;
        this.applyBackpressure = applyBackpressure;
    }


    @Override
    public Identifier next() {
        synchronized (sequenceLock) {
            increment();
            ByteBuffer sequenceBytes =
                    ByteBuffer.allocate(ID_SIZE);
            return Identifier.valueOf(
                    sequenceBytes
                            .putLong(currentTime)
                            .put(seed)
                            .putShort((short) sequence).array()
            );
        }
    }

    private void increment() {
        currentTime = clock.millis();

        if (currentTime != previousTime) {
            sequence = 0;
            previousTime = currentTime;
        } else if (sequence == SEQUENCE_MAXIMUM) {
            if(applyBackpressure) {
                try {
                    Thread.sleep(1);
                    increment();
                } catch(InterruptedException ex) {
                }
            }
            throw new IllegalArgumentException("Attempting to generate sequences too quickly.  " +
                    "Can't guarantee uniqueness.  " +
                    "Try using another sequence instance (with another clock)");
        } else {
            sequence++;
        }
    }

    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public InetAddress getNodeIdentity() {
        return nodeIdentity;
    }


    private void check(Clock clock, InetAddress node) {
        Objects.requireNonNull(node, "Node must not be null!");
        Objects.requireNonNull(clock, "Clock must not be null!");
    }


}
