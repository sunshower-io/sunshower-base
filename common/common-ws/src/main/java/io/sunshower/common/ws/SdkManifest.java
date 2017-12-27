package io.sunshower.common.ws;

import org.eclipse.persistence.jaxb.JAXBContextProperties;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.moxy.xml.MoxyXmlFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.context.ApplicationContext;



public class SdkManifest extends ResourceConfig implements ResourceConfigCustomizer {


    static final Logger log = LoggerFactory.getLogger(SdkManifest.class);
    final ApplicationContext applicationContext;

    public SdkManifest(ApplicationContext ctx) {
        this.applicationContext = ctx;
    }

    private Map<String, Object> properties() {
        final Map<String, Object> props = new HashMap<>();
        props.put(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        return props;
    }


    private void registerAll(ApplicationContext ctx, ResourceConfig config) {
        log.info("Auto-registering SDK components...");
        ConfigurableListableBeanFactory factory =
                (ConfigurableListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        int count = 0;
        for (String name : factory.getBeanDefinitionNames()) {
            count = registerPath(factory, config, count, name);
            registerProviders(factory, name);
        }
        log.info("Successfully registered {} services", count);
    }

    private void registerProviders(ConfigurableListableBeanFactory factory, String name) {
        Provider provider = factory.findAnnotationOnBean(name, Provider.class);
        if (provider != null) {
            Class<?> type = factory.getType(name);
            log.info("\tRegistering provider of type '{}'", type);
            register(type);
        }
    }

    private int registerPath(ConfigurableListableBeanFactory factory, ResourceConfig cfg, int count, String name) {
        Path path = factory.findAnnotationOnBean(name, Path.class);
        if (path != null) {
            count++;
            Class<?> type = factory.getType(name);
            log.info("\tbound '{}' to '{}' at path '{}'", name, type, path.value());
            if(!Providers.class.equals(type)) {
                cfg.register(type);
            }
        }
        return count;
    }


    @Override
    public void customize(ResourceConfig config) {
        registerAll(applicationContext, config);
        log.info("\t Registering media-type/accept application/text+json -> Moxy Provider");
        config.register(new MoxyXmlFeature(
                properties(),
                Thread.currentThread().getContextClassLoader(),
                false
        ));
        config.register(IdentifierParameterConverter.class);

        config.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        config.property(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        config.register(MoxyJsonConfig.class);
        config.register(MoxyJsonFeature.class);
    }
}