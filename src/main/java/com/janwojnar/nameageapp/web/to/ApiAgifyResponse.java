package com.janwojnar.nameageapp.web.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiAgifyResponse {
    String name;
    Integer age;
}
