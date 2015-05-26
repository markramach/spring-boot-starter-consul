/**
 * 
 */
package com.flyover.boot.consul.config;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.mock.env.MockEnvironment;

/**
 * @author mramach
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsulPropertySourceConfigurationTest {

    @Spy
    private ConfigurableEnvironment environment = new MockEnvironment();
    @Mock
    private AnnotationMetadata metadata;
    @Mock
    private BeanDefinitionRegistry registry;
    @InjectMocks
    private ConsulPropertySourceConfiguration consulPropertySourceConfiguration;
    
    @Test
    public void testRegisterBeanDifinitions() {
        
        Map<String, Object> attributes = Collections
                .singletonMap("value", new String[]{"my/test/path"});
        
        when(metadata.getAnnotationAttributes(eq(EnableConsulPropertySource.class.getName())))
            .thenReturn(attributes);
        
        int size = environment.getPropertySources().size();
        
        consulPropertySourceConfiguration.registerBeanDefinitions(metadata, registry);

        assertEquals("Checking that the property source was added to the environment.", 
                ++size, environment.getPropertySources().size());
        
    }
    
}
