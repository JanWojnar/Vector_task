package com.janwojnar.nameageapp.persistance;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Scanning of configurations in this package.
 */
@Configuration
@ComponentScan
@ConfigurationPropertiesScan
public class PersistanceConfig {
}
