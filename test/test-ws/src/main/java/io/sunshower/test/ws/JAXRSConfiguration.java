package io.sunshower.test.ws;

import io.sunshower.common.ws.SdkManifest;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ClientConfiguration;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.providers.DefaultTextPlain;
import org.jboss.resteasy.plugins.providers.StringTextStar;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.ext.Providers;
import java.util.ArrayList;
import java.util.List;

public class JAXRSConfiguration {
    
    @Bean
    public WebTarget sunshowerWebTarget(
            Client client, 
            EnableJAXRS configuration,
            SunshowerTestServer server
    ) {
        return client.target(server.getLocation());
    }
    
    
    @Bean
    @DependsOn("sunshowerWebTarget")
    public JAXRSPostProcessor jaxrsPostProcessor(ApplicationContext context) {
        return new JAXRSPostProcessor((ConfigurableListableBeanFactory) context.getAutowireCapableBeanFactory());
    }
    
    @Bean
    public SunshowerTestServer sunshowerTestServer(EnableJAXRS configuration, ResteasyDeployment deployment) {
        return new SunshowerTestServer(configuration, deployment);
    }
    
    @Bean
    public static ProviderDependencyReorderingFactoryPostProcessor providerDependencyReorderingFactoryPostProcessor() {
        return new ProviderDependencyReorderingFactoryPostProcessor();
    }
    
    @Bean
    public Client jaxRsClient(
            ResteasyProviderFactory factory
    ) {
        return ClientBuilder.newClient(new ClientConfiguration(factory));
    } 
    
   
    
    @Bean
    public JsonProvider jsonProvider() {
        JsonProvider provider = new JsonProvider();
        return provider;
    }
    
    @Bean
    public StringTextStar stringTextStar() {
        return new StringTextStar();
    }
    
    @Bean
    public DefaultTextPlain defaultTextPlain() {
        return new DefaultTextPlain();
    }
    
    @Bean
    public Dispatcher dispatcher(ResteasyProviderFactory providerFactory, Registry registry) {
        return new SynchronousDispatcher(providerFactory, (ResourceMethodRegistry) registry);
    }
   
    @Bean
    public Registry resteasyRegistry(ResteasyProviderFactory factory) {
        return new ResourceMethodRegistry(factory);
    }
    
    @Bean
    public ResteasyProviderFactory resteasyProviderFactory() {
        return new ResteasyProviderFactory();
    }
    
    
    @Bean
    public ResteasyDeployment resteasyDeployment(
            Registry registry,
            Dispatcher dispatcher,
            ResteasyProviderFactory factory
    ) {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setRegistry(registry);
        deployment.setProviderFactory(factory);
        deployment.setDispatcher(dispatcher);
        return deployment;
    }

    @Bean
    @Order
    public SpringBeanProcessor springBeanProcessor(
            Registry registry,
            ResteasyDeployment deployment,
            ResteasyProviderFactory factory
            
    ) {
        factory.setRegisterBuiltins(true);
        SpringBeanProcessor processor = new SpringBeanProcessor();
        processor.setProviderFactory(factory);
        processor.setRegistry(registry);
        processor.setDispatcher(deployment.getDispatcher());
        return processor;
    }



}
