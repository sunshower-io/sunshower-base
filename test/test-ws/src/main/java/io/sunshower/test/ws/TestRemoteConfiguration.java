package io.sunshower.test.ws;

import io.sunshower.common.ws.SdkManifest;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.git.FirstTokenBranchResolver;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Paths;
import java.util.Arrays;

@ContextConfiguration
public class TestRemoteConfiguration {


    @Bean
    public SdkManifest sdkManifest(ApplicationContext context) {
        return new SdkManifest(context);
    }


}
