/**
 * 
 */
package com.flyover.boot.consul.config;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author mramach
 *
 */
@Configuration
public class ConsulPropertySourceConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private ConfigurableEnvironment environment;
    
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, 
            BeanDefinitionRegistry registry) {
        
        ConsulProperties configuration = override(metadata, new ConsulProperties());
        ConsulAdapter consulAdapter = new ConsulAdapter(configuration);

        environment.getPropertySources().addFirst(
                new ConsulPropertySource(configuration, consulAdapter));
        
    }
    
    private ConsulProperties override(AnnotationMetadata metadata, ConsulProperties configuration) {
        
        Map<String, Object> attributes = metadata
                .getAnnotationAttributes(EnableConsulPropertySource.class.getName());
        
        configuration.setEndpoint(environment.getProperty("consul.endpoint", "http://localhost:8500/v1"));
        configuration.setPaths(Arrays.asList((String[])attributes.get("value")));
        configuration.setFailFast(Boolean.valueOf(environment.getProperty("consul.failFast", "false")));
        
        return configuration;
        
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

}
