package io.sunshower.test.ws;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.MergedContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoteContextBootstrapper extends SpringBootTestContextBootstrapper {
    public RemoteContextBootstrapper() {
        
    }

    protected Class<?>[] getOrFindConfigurationClasses(
            MergedContextConfiguration mergedConfig) {
        Class<?>[]           cfgs = super.getOrFindConfigurationClasses(mergedConfig);
        List<Class<?>> configClasses = new ArrayList<>(Arrays.asList(cfgs));
        configClasses.add(TestRemoteConfiguration.class);
        return configClasses.toArray(new Class<?>[cfgs.length]);
    }
    
    
    
}
