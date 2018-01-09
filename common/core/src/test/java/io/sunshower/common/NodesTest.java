package io.sunshower.common;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class NodesTest {

  @Test
  public void ensureLocalAddressWorks() {
    assertThat(Nodes.localAddress(), is(not(nullValue())));
  }

  @Test
  public void ensureGetIdentifiableNodeHardwareAddress() {
    Nodes.getIdentifiableNodeHardwareAddress(true);
  }

  @Test
  public void ensureGetLocalIdentityWorks() {
    assertThat(Nodes.localIdentity(), is(not(0)));
  }

  @Test
  public void ensureGetLocalIdentityFromInetAddressWorks() {
    assertThat(Nodes.localIdentity(Nodes.localAddress()), is(not(0)));
  }
}
