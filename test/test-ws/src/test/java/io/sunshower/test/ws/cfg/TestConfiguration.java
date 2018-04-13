package io.sunshower.test.ws.cfg;

import org.jboss.resteasy.plugins.providers.sse.SseEventProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

  @Bean
  public SseEventProvider sseEventProvider() {
    return new SseEventProvider();
  }

  @Bean
  public TestService testService() {
    return new DefaultTestService();
  }
}
