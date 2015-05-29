/**
 * 
 */
package com.flyover.boot.consul.config;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
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
@SuppressWarnings("unchecked")
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

        configuration.setPaths(Arrays.asList("path/to/property", "path/to/another/property"));
        
        when(consulAdapter.get(isA(String.class), eq(true))).thenReturn(
                Collections.singletonMap("hello", "world"), 
                Collections.singletonMap("hello_again", "world"));
        
        PropertySource<?> propertySource = locator.locate(new StandardEnvironment());
        
        assertNotNull("Checking that a non-null property sources was returned.", propertySource);
        
        assertTrue("Checking that the property source contains a property.", 
                propertySource.containsProperty("hello"));
        
        assertEquals("Checking that the property source contains the expected value.", 
                "world", propertySource.getProperty("hello"));
        
        assertTrue("Checking that the property source contains a property.", 
                propertySource.containsProperty("hello_again"));
        
        assertEquals("Checking that the property source contains the expected value.", 
                "world", propertySource.getProperty("hello_again"));
        
    }

}
