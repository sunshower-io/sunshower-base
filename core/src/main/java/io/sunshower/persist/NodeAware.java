package io.sunshower.persist;

import java.net.InetAddress;


public interface NodeAware {

    InetAddress getNodeIdentity();
}
