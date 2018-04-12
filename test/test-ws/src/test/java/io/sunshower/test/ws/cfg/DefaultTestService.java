package io.sunshower.test.ws.cfg;

import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

public class DefaultTestService implements TestService {

  public void subscribe(String id, SseEventSink sink, Sse sse) {

    new Thread(
            () -> {
              try {
                sink.send(
                    sse.newEventBuilder()
                        .name("domain-progress")
                        .data(String.class, "starting domain " + id + " ...")
                        .build());
                Thread.sleep(200);
                sink.send(sse.newEvent("domain-progress", "50%"));
                Thread.sleep(200);
                sink.send(sse.newEvent("domain-progress", "60%"));
                Thread.sleep(200);
                sink.send(sse.newEvent("domain-progress", "70%"));
                Thread.sleep(200);
                sink.send(sse.newEvent("domain-progress", "99%"));
                Thread.sleep(200);
                sink.send(sse.newEvent("domain-progress", "Done."))
                    .thenAccept(
                        (Object obj) -> {
                          sink.close();
                        });
              } catch (final InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
  }

  @Override
  public TestEntity save(TestEntity testEntity) {
    return testEntity;
  }

  @Override
  public String call(String input) {
    return String.format("Called with: %s", input);
  }
}
