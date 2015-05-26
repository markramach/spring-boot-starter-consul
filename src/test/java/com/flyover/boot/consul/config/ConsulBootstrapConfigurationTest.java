/**
 * 
 */
package com.flyover.boot.consul.config;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author mramach
 *
 */
public class ConsulBootstrapConfigurationTest {

    @Test
    public void testConsulPropertySourceLocatorCreated() {
        // PropertySourceBootstrapConfiguration.class
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
                ConsulBootstrapConfiguration.class).web(false).run();
        
        assertNotNull("Checking that the consul adapter is available in the application context.",
                BeanFactoryUtils.beanOfType(context, ConsulAdapter.class));
        
        assertNotNull("Checking that the property source locator is available in the application context.",
                BeanFactoryUtils.beanOfType(context, ConsulPropertySourceLocator.class));
        
    }
    
    @Test
    public void testConsulPropertiesCreated_WithDefaultProperties() {
        
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
                ConsulBootstrapConfiguration.class).web(false).run();
        
        ConsulProperties properties = BeanFactoryUtils.beanOfType(context, ConsulProperties.class);
        
        assertNotNull("Checking that the consul adapter is available in the application context.", properties);
        assertEquals("Checking that the endpoint has been defaulted.", "http://localhost:8500/v1", properties.getEndpoint());
        assertEquals("Checking that the fast fail option has been defaulted.", false, properties.isFailFast());
        
    }
    
    @Test
    public void testConsulPropertiesCreated_WithEnvironmentProperties() {
        
        String endpoint = "http://localhost:8080/v1";
        
        ConfigurableEnvironment environment = new StandardEnvironment() {

            /* (non-Javadoc)
             * @see org.springframework.core.env.StandardEnvironment#customizePropertySources(org.springframework.core.env.MutablePropertySources)
             */
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                
                super.customizePropertySources(propertySources);
                
                propertySources.addFirst(new MapPropertySource("consul-properties", 
                        Collections.singletonMap("consul.endpoint", endpoint)));
                
            }
            
        };
        
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
                ConsulBootstrapConfiguration.class).environment(environment).web(false).run();
        
        ConsulProperties properties = BeanFactoryUtils.beanOfType(context, ConsulProperties.class);
        
        assertNotNull("Checking that the consul adapter is available in the application context.", properties);
        assertEquals("Checking that the endpoint has been defaulted.", endpoint, properties.getEndpoint());
        
    }
    
}
