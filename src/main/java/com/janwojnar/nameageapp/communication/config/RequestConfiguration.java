package com.janwojnar.nameageapp.communication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janwojnar.nameageapp.common.JsonPrettifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration of beans that are useful in web communication.
 */
@Configuration
public class RequestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JsonPrettifier jsonPrettifier(ObjectMapper objectMapper) {
        return new JsonPrettifier(objectMapper);
    }
}
