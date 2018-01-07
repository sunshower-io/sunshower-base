package io.sunshower.common.ws;

import org.springframework.context.ApplicationContext;

import javax.ws.rs.core.Application;
import java.util.logging.Logger;

public class SdkManifest extends Application {
    static final Logger log = Logger.getLogger(SdkManifest.class.getName());
    
    
    public SdkManifest(ApplicationContext context) {
        scan(context);
    }

    private void scan(ApplicationContext context) {
        
    }
}
