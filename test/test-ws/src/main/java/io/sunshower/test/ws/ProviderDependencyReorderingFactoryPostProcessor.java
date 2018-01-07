package io.sunshower.test.ws;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProviderDependencyReorderingFactoryPostProcessor implements BeanFactoryPostProcessor {
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames     = beanFactory.getBeanNamesForType(Client.class);
        String[] providerNames = beanFactory.getBeanNamesForAnnotation(Provider.class);
        for(String name : beanNames) {
            BeanDefinition definition = beanFactory.getBeanDefinition(name);
            for(String providerName : providerNames) {
                String[] dependencies = reconfigure(providerName, definition.getDependsOn());
                definition.setDependsOn(dependencies);
            }
        }
        
        
    }

    private String[] reconfigure(String providerName, String[] dependsOn) {
        if(dependsOn == null) {
            return new String[]{providerName};
        }
        List<String> result = new ArrayList<>(Arrays.asList(dependsOn));
        result.add(providerName);
        return result.toArray(new String[dependsOn.length + 1]);
    }
}
