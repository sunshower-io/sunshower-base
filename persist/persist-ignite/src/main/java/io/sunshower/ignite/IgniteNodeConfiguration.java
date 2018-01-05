package io.sunshower.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Singleton;


@Configuration
public class IgniteNodeConfiguration {

    @Bean
    public IgniteConfiguration igniteConfiguration(IgniteConfigurationSource source) {
        return source.toNative();
    }


    @Singleton
    @Bean(destroyMethod = "close")
    public static Ignite ignite(IgniteConfiguration configuration) {

        return Ignition.getOrStart(configuration);
    }

}
