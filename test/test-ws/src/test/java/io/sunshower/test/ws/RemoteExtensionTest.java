package io.sunshower.test.ws;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

import io.sunshower.test.ws.cfg.TestConfiguration;
import io.sunshower.test.ws.cfg.TestEntity;
import io.sunshower.test.ws.cfg.TestService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableJAXRS
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
class RemoteExtensionTest {

  @Inject private Client client;

  @Inject private TestService local;

  @Remote private TestService remote;

  @Inject private WebTarget webTarget;

  @Test
  void ensureCustomSseWorks() throws InterruptedException, ExecutionException, TimeoutException {

    ResteasyWebTarget path =
        ((ResteasyWebTarget) webTarget).path(TestService.class).path("1/custom");
    SseEventSource s = SseEventSource.target(path).reconnectingEvery(10, TimeUnit.SECONDS).build();
    AtomicInteger i = new AtomicInteger();
    s.register(
        e -> {
          System.out.println(e.readData());
          i.incrementAndGet();
        },
        System.out::println);
    s.open();
    Executors.newWorkStealingPool()
        .submit(
            () -> {
              System.out.println(i);
              while (i.get() < 1) {
                Thread.yield();
              }
            })
        .get(10, TimeUnit.SECONDS);
  }

  @Test
  void ensureSseWorks() throws InterruptedException, TimeoutException, ExecutionException {

    ResteasyWebTarget path =
        ((ResteasyWebTarget) webTarget).path(TestService.class).path("1/events");
    SseEventSource s = SseEventSource.target(path).reconnectingEvery(10, TimeUnit.SECONDS).build();
    AtomicInteger i = new AtomicInteger();
    s.register(
        e -> {
          System.out.println(e.readData(String.class));
          i.incrementAndGet();
        },
        System.out::println);
    s.open();
    Executors.newWorkStealingPool()
        .submit(
            () -> {
              System.out.println(i);
              while (i.get() < 6) {
                Thread.yield();
              }
            })
        .get(10, TimeUnit.SECONDS);
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
