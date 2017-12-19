package io.sunshower.persist;


import io.sunshower.common.Identifier;
import io.sunshower.common.Nodes;

import java.time.Clock;

/**
 * Created by haswell on 7/18/17.
 */
public class Identifiers {

    public static Sequence<Identifier> randomSequence() {
        return new UUIDSequence();
    }

    public static Sequence<Identifier> newSequence() {
        return newSequence(true);
    }

    public static Sequence<Identifier> newSequence(boolean backpressure) {
        return newSequence(Clock.systemUTC(), backpressure);
    }

    public static Sequence<Identifier> newSequence(
            Clock clock,
            boolean applyBackpressure
    ) {
        return new IDSequence(
                clock,
                Nodes.getIdentifiableNodeHardwareAddress(),
                Nodes.getIdentifiableNetworkAddress(),
                applyBackpressure
        );
    }
}
