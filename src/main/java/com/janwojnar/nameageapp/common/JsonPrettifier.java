package com.janwojnar.nameageapp.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPrettifier {

    private final ObjectMapper objectMapper;

    public JsonPrettifier(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createPrettyJson(Object o) {
        try {
            return this.objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
