package com.janwojnar.nameageapp.web.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class ApiAgifyResponse {
    String name;
    Integer age;
}
