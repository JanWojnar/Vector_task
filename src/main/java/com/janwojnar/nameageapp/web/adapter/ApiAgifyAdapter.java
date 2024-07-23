package com.janwojnar.nameageapp.web.adapter;

import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ApiAgifyAdapter {

    private final RestTemplate restTemplate;

    private static final String name_age_endpoint = "/?name={askedName}";

    private static final String baseUrl = "https://api.agify.io";

    public ApiAgifyResponse getNameData(String askedName) {

        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("askedName", askedName);

        log.debug("Request GET on url " + baseUrl + name_age_endpoint + " with params " + urlVariables);

        return this.restTemplate.getForObject(baseUrl.concat(name_age_endpoint), ApiAgifyResponse.class, urlVariables);
    }
}
