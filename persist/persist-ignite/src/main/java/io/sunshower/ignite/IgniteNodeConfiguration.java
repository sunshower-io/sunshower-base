package io.sunshower.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;


@Configuration
@EnableConfigurationProperties(IgniteConfigurationSource.class)
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
