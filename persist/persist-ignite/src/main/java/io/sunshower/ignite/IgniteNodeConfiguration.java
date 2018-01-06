package io.sunshower.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;


@Configuration
public class IgniteNodeConfiguration {

    @Bean
    public IgniteConfigurationSource igniteConfigurationSource(ConfigurationProvider provider) {
        final String fabricName = provider.getProperty("ignite.fabricName", String.class);
        final IgniteConfigurationSource.IgniteMemorySettings memorySettings =
                provider.bind(
                        "ignite.memory",
                        IgniteConfigurationSource.IgniteMemorySettings.class
                );

        final IgniteConfigurationSource.IgniteDiscoverySettings discoverySettings =
                provider.bind(
                        "ignite.discovery",
                        IgniteConfigurationSource.IgniteDiscoverySettings.class
                );
        
        return new IgniteConfigurationSource(fabricName, memorySettings, discoverySettings);
    }

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
