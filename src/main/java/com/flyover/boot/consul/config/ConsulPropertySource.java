/**
 * 
 */
package com.flyover.boot.consul.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;

/**
 * @author mramach
 *
 */
public class ConsulPropertySource extends EnumerablePropertySource<Map<String, Object>> {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConsulPropertySourceLocator.class);
    
    private Map<String, Object> source = new LinkedHashMap<String, Object>();

    public ConsulPropertySource(ConsulProperties configuration, ConsulAdapter consulAdapter) {
        
        super("consul-property-source-" + UUID.randomUUID().toString());
        
        try {
            
            configuration.getPaths().forEach(p -> consulAdapter.get(p, true)
                    .entrySet().forEach(e -> source.put(pathToProperty(e.getKey()), e.getValue())));
            
        } catch (RuntimeException e) {
            
            if(configuration.isFailFast()) {
                throw e;
            }
            
            LOG.info("Unable to fetch properties from resource {}.", configuration.getEndpoint());
            
        }
        
    }
    
    private String pathToProperty(String path) {
        return path.replace('/', '.');
    }
    
    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[source.size()]);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }

}
