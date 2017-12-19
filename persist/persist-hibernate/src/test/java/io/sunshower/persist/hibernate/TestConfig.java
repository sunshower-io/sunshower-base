package io.sunshower.persist.hibernate;

import io.sunshower.persist.DefaultIndexedEntityService;
import io.sunshower.persist.IndexedEntityService;
import io.sunshower.persistence.annotations.CacheMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by haswell on 2/20/17.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@CacheMode(CacheMode.Mode.Local)
public class TestConfig {

    @Bean
    public Pcfg pcfg() {
        return new Pcfg();
    }

    @Bean
    public IndexedEntityService indexedEntityService() {
        return new DefaultIndexedEntityService();
    }


}
