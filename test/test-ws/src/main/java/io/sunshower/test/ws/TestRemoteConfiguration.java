package io.sunshower.test.ws;

import io.sunshower.common.ws.SdkManifest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TestRemoteConfiguration {

    @Bean
    public SdkManifest sdkManifest(ApplicationContext context) {
        return new SdkManifest(context);
    }
    
    
}
