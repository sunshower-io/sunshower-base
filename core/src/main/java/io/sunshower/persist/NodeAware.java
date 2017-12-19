package io.sunshower.persist;

import java.net.InetAddress;

/**
 * Created by haswell on 7/20/17.
 */
public interface NodeAware {

    InetAddress getNodeIdentity();
}
