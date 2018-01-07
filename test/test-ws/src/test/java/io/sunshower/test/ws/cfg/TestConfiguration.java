package io.sunshower.test.ws.cfg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TestConfiguration {
    

   
    
    @Bean
    public TestService testService() {
        return new DefaultTestService();
    }
}
