package io.sunshower.ignite;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.xml.bind.annotation.XmlValue;


@ConfigurationProperties("ignite")
public class IgniteConfigurationSource {

    @Value("${ignite.fabric-name}")
    private String fabricName;



    private IgniteMemorySettings memory;

    private IgniteDiscoverySettings discovery;

    public String getFabricName() {
        return fabricName;
    }


    public IgniteMemorySettings getMemory() {
        return memory;
    }

    public void setMemory(IgniteMemorySettings memory) {
        this.memory = memory;
    }

    public void setFabricName(String fabricName) {
        this.fabricName = fabricName;
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
            if(discovery.getMode() != null && discovery.getMode().trim().equals("vm-local")) {

                TcpDiscoverySpi disco = new TcpDiscoverySpi()
                        .setIpFinder(new TcpDiscoveryVmIpFinder(true));
                cfg.setDiscoverySpi(disco);
            }
        }
    }

    public IgniteDiscoverySettings getDiscovery() {
        return discovery;
    }

    public void setDiscovery(IgniteDiscoverySettings discovery) {
        this.discovery = discovery;
    }

    protected CacheConfiguration<?, ?> cacheConfiguration() {
        final CacheConfiguration<?, ?> cfg = new CacheConfiguration<>();
        cfg.setName(this.fabricName == null ? "default-ignite-instance" : this.fabricName);
        return cfg;
    }

    protected long externalMax() {
        return memory != null ? memory.max : 0;
    }



    public static class IgniteMemorySettings {

        private long max;
        private String mode;


        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }
    }

    public static class IgniteDiscoverySettings {

        private String mode;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }
}
