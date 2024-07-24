package com.janwojnar.nameageapp.communication.adapter.impl;

import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.communication.adapter.ApiAgifyAdapter;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@AllArgsConstructor
@Slf4j
public class ApiAgifyAdapterImpl implements ApiAgifyAdapter {

    private static final String NAME_AGE_ENDPOINT = "/?name={name}";

    private static final String BASE_URL = "https://api.agify.io";

    private final RestTemplate restTemplate;

    @Override
    public ApiAgifyResponse getNameData(String name) {

        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("name", name);

        log.debug(LogPreparer.prepareLog("Request GET on url ", BASE_URL, NAME_AGE_ENDPOINT, " with params ",
                urlVariables));

        return this.restTemplate.getForObject(BASE_URL.concat(NAME_AGE_ENDPOINT), ApiAgifyResponse.class, urlVariables);
    }
}
