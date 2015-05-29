/**
 * 
 */
package com.flyover.boot.consul.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
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
            
            configuration.getPaths().stream()
                .map(p -> consulAdapter.get(p, true))
                    .reduce(new LinkedHashMap<String, Object>(), (l, r) -> {l.putAll(r); return l;})
                        .entrySet().forEach(this::setProperty);
            
        } catch (RuntimeException e) {
            
            if(configuration.isFailFast()) {
                throw e;
            }
            
            LOG.info("Unable to fetch properties from resource {}.", configuration.getEndpoint());
            
        }
        
    }

    private Object setProperty(Entry<String, Object> e) {
        return source.put(pathToProperty(e.getKey()), e.getValue());
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
