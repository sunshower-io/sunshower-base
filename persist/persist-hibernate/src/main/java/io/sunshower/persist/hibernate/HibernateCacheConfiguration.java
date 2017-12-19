package io.sunshower.persist.hibernate;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by haswell on 5/3/17.
 */
public class HibernateCacheConfiguration {

    @Value("${jpa.provider.cache.fabric-name")
    private String fabricName;

    @Value("${jpa.provider.cache.enable-l2}")
    private boolean enabled;


    @Value("${jpa.provider.cache.provider}")
    private String provider;

    @Value("${jpa.provider.cache.enable-query-cache")
    private boolean queryCacheEnabled;

    @Value("${jpa.provider.cache.generate-statistics")
    private boolean generateStatistics;

    @Value("${jpa.provider.cache.region-factory")
    private String regionFactory;


    @Value("${jpa.provider.cache.release-mode")
    private String releaseMode;


    @Value("${jpa.provider.cache.default-access-type")
    private String accessType;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isQueryCacheEnabled() {
        return queryCacheEnabled;
    }

    public void setQueryCacheEnabled(boolean queryCacheEnabled) {
        this.queryCacheEnabled = queryCacheEnabled;
    }

    public boolean isGenerateStatistics() {
        return generateStatistics;
    }

    public void setGenerateStatistics(boolean generateStatistics) {
        this.generateStatistics = generateStatistics;
    }

    public String getRegionFactory() {
        return regionFactory;
    }

    public void setRegionFactory(String regionFactory) {
        this.regionFactory = regionFactory;
    }

    public String getReleaseMode() {
        return releaseMode;
    }

    public void setReleaseMode(String releaseMode) {
        this.releaseMode = releaseMode;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getFabricName() {
        return fabricName;
    }

    public void setFabricName(String fabricName) {
        this.fabricName = fabricName;
    }
}
