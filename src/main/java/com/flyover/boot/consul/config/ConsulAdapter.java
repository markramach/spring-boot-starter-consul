/**
 * 
 */
package com.flyover.boot.consul.config;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author mramach
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConsulAdapter {
    
    @Autowired
    private ConsulProperties configuration;
    
    public ConsulAdapter(ConsulProperties configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets all properties located at the specified path.
     * 
     * @param path The path to use when resolving properties.
     * @param recurse True if recursive searching should be enabled, false otherwise.
     * @return Map of all properties located under the provided path.
     */
    public Map<String, Object> get(String path, boolean recurse) {
        
        ResponseEntity<List> response = new RestTemplate().getForEntity(
                String.format(configuration.getEndpoint() + "/kv/{path}?%s", (recurse ? "recurse" : "")), 
                    List.class, path);

        List<Map<String, Object>> values = (List<Map<String, Object>>)response.getBody();
        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        
        values.stream()
                .filter(i -> i.get("Value") != null)
                    .forEach(i -> {
                        
            properties.put((String) i.get("Key"), 
                    new String(Base64.getDecoder().decode((String) i.get("Value"))));
            
        });
        
        return properties;
        
    }
    
}
