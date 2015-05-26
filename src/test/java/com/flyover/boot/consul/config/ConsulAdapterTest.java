/**
 * 
 */
package com.flyover.boot.consul.config;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * @author mramach
 *
 */
public class ConsulAdapterTest {
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8500);
    
    @Test
    public void testGetProperties() throws Exception {
        
        stubGetResponse();
        
        ConsulAdapter adapter = new ConsulAdapter(new ConsulProperties());
        
        Map<String, Object> properties = adapter.get("root/flyover", true);
        
        verify(getRequestedFor(urlMatching("/v1/kv/root/flyover.*"))
                .withQueryParam("recurse", matching(".*")));
        
        assertTrue(properties.containsKey("root/flyover/property"));
        assertEquals("Hello World!", properties.get("root/flyover/property"));
        
    }
    
    @Test
    public void testGetProperties_NoRecurse() throws Exception {
        
        stubGetResponse();
        
        ConsulAdapter adapter = new ConsulAdapter(new ConsulProperties());
        
        adapter.get("root/flyover", false);
        
        verify(getRequestedFor(urlMatching("/v1/kv/root/flyover?")));
        
    }

    private void stubGetResponse() throws URISyntaxException, IOException {
        
        Path responsePath = Paths.get(Thread.currentThread().getContextClassLoader().
                getResource("get_kv_response.json").toURI());
        
        stubFor(get(urlMatching("/v1/kv/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(Files.readAllBytes(responsePath))));
        
    }

}
