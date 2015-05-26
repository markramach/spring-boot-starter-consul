/**
 * 
 */
package com.flyover.boot.consul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 * @author mramach
 *
 */
public class ConsulPropertySourceLocator implements PropertySourceLocator {
    
    @Autowired
    private ConsulProperties configuration;
    @Autowired
    private ConsulAdapter consulAdapter;
    
    @Override
    public PropertySource<?> locate(Environment environment) {
        return new ConsulPropertySource(configuration, consulAdapter);
    }

}
