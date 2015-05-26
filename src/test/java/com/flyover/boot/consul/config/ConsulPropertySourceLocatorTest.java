/**
 * 
 */
package com.flyover.boot.consul.config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author mramach
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsulPropertySourceLocatorTest {

    @Spy
    private ConsulProperties configuration = new ConsulProperties();
    @Mock
    private ConsulAdapter consulAdapter;
    @InjectMocks
    private ConsulPropertySourceLocator locator;
    
    @Test
    public void testLocate() {

        String path = "path/to/property";
        
        configuration.setPaths(Arrays.asList(path));
        
        when(consulAdapter.get(eq(path), eq(true)))
            .thenReturn(Collections.singletonMap("hello", "world"));
        
        PropertySource<?> propertySource = locator.locate(new StandardEnvironment());
        
        assertNotNull("Checking that a non-null property sources was returned.", propertySource);
        
        assertTrue("Checking that the property source contains a property.", 
                propertySource.containsProperty("hello"));
        
        assertEquals("Checking that the property source contains the expected value.", 
                "world", propertySource.getProperty("hello"));
        
    }

}
