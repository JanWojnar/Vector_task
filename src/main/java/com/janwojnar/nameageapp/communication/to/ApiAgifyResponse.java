package com.janwojnar.nameageapp.communication.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * Agify API Response object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class ApiAgifyResponse {
    String name;
    Integer age;
}
