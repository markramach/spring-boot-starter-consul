/**
 * 
 */
package com.flyover.boot.consul.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mramach
 *
 */
@ConfigurationProperties(ConsulProperties.PREFIX)
public class ConsulProperties {
    
    public static final String PREFIX = "consul";
    
    private String endpoint = "http://localhost:8500/v1";
    private boolean failFast = false;
    private List<String> paths = new LinkedList<String>();

    /**
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the failFast
     */
    public boolean isFailFast() {
        return failFast;
    }

    /**
     * @param failFast the failFast to set
     */
    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    /**
     * @return the paths
     */
    public List<String> getPaths() {
        return paths;
    }

    /**
     * @param paths the paths to set
     */
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

}
