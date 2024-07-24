package com.janwojnar.nameageapp.persistance.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties of text database.
 */
@ConfigurationProperties(prefix = "database")
@Setter
@Getter
public class DatabaseConfigurationProperties {
    private String path;
}
