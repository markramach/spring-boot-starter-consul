/**
 * 
 */
package com.flyover.boot.consul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author mramach
 *
 */
@Configuration
@EnableConfigurationProperties
public class ConsulBootstrapConfiguration {
    
    @Autowired
    private Environment environment;
    
    @Bean
    @ConditionalOnProperty(value = "consul.enabled", matchIfMissing = true)
    public ConsulProperties consulProperties() {
        return new ConsulProperties();
    }
    
    @Bean 
    @ConditionalOnProperty(value = "consul.enabled", matchIfMissing = true)
    public ConsulAdapter consulAdapter(ConsulProperties configuration) {
        return new ConsulAdapter(configuration);
    }

    @Bean
    @ConditionalOnProperty(value = "consul.enabled", matchIfMissing = true)
    public ConsulPropertySourceLocator consulPropertySourceLocator() {
        return new ConsulPropertySourceLocator();
    }
    
}
