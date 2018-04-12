package io.sunshower.test.ws;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

import io.sunshower.test.ws.cfg.TestConfiguration;
import io.sunshower.test.ws.cfg.TestEntity;
import io.sunshower.test.ws.cfg.TestService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableJAXRS
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
class RemoteExtensionTest {

  @Inject private Client client;

  @Inject private TestService local;

  @Remote private TestService remote;

  @Inject private WebTarget webTarget;

  @Test
  void ensureSseWorks() throws InterruptedException {

    ResteasyWebTarget path =
        ((ResteasyWebTarget) webTarget).path(TestService.class).path("1/events");
    SseEventSource s = SseEventSource.target(path).reconnectingEvery(10, TimeUnit.SECONDS).build();
    System.out.println("a");
    s.register(
        e -> {
          System.out.println("d");
          System.out.println(e.readData(String.class));
          System.out.println("e");
        },
        System.out::println);
    System.out.println("b");
    s.open();
    System.out.println("c");
    Thread.sleep(1000);
  }

  @Test
  public void ensureClientIsInjected() {
    assertThat(client, is(not(nullValue())));
  }

  @Test
  public void ensureWebTargetIsInjected() {
    assertThat(webTarget, is(not(nullValue())));
  }

  @Test
  public void ensureRemoteServiceIsInjected() {
    assertThat(remote, is(not(nullValue())));
  }

  @Test
  public void ensureServiceGetsInjected() {
    assertThat(local, is(not(nullValue())));
  }

  @Test
  public void ensureRemoteIsInjected() {
    assertThat(remote, is(not(nullValue())));
  }

  @Test
  public void ensureRemoteTargetIsCallableWithJaxRSEntity() {
    assertThat(remote.save(new TestEntity("hello")).getName(), is("hello"));
  }

  @Test
  public void ensureRemoteTargetIsCallable() {
    assertThat(remote.call("frapper"), is("Called with: frapper"));
  }
}
