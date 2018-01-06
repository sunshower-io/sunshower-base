package io.sunshower.ignite;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;


public class IgniteConfigurationSource {

    private final String fabricName;

    private final IgniteMemorySettings memory;

    private final IgniteDiscoverySettings discovery;

    public IgniteConfigurationSource(
            String name,
            IgniteMemorySettings memory, 
            IgniteDiscoverySettings discovery
    ) {
        this.memory = memory;
        this.fabricName = name;
        this.discovery = discovery;
    }


    public String getFabricName() {
        return fabricName;
    }


    public IgniteMemorySettings getMemory() {
        return memory;
    }


    public IgniteConfiguration toNative() {
        final IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName(this.fabricName);
        cfg.setCacheConfiguration(cacheConfiguration());
        configureDiscovery(cfg);
        return cfg;
    }

    private void configureDiscovery(IgniteConfiguration cfg) {
        if(discovery != null) {
            if(discovery.mode() != null && discovery.mode().trim().equals("vm-local")) {

                TcpDiscoverySpi disco = new TcpDiscoverySpi()
                        .setIpFinder(new TcpDiscoveryVmIpFinder(true));
                cfg.setDiscoverySpi(disco);
            }
        }
    }

    public IgniteDiscoverySettings getDiscovery() {
        return discovery;
    }


    protected CacheConfiguration<?, ?> cacheConfiguration() {
        final CacheConfiguration<?, ?> cfg = new CacheConfiguration<>();
        cfg.setName(this.fabricName == null ? "default-ignite-instance" : this.fabricName);
        return cfg;
    }

    protected long externalMax() {
        return memory != null ? memory.max() : 0;
    }



    public interface IgniteMemorySettings {

        long max();
        String mode();
    }

    public interface IgniteDiscoverySettings {

        default String mode() {
            return "vm-local";
        }
    }
}
