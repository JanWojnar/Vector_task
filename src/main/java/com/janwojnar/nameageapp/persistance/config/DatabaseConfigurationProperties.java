package com.janwojnar.nameageapp.persistance.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "database")
@Setter
@Getter
public class DatabaseConfigurationProperties {
    private String path;
}
