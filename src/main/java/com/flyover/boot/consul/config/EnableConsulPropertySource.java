/**
 * 
 */
package com.flyover.boot.consul.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author mramach
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ConsulPropertySourceConfiguration.class)
public @interface EnableConsulPropertySource {

    /**
     * The path value(s) to use when looking up key-value pairs from consul.
     * 
     * @return The path value(s).
     */
    String[] value();
    
}
