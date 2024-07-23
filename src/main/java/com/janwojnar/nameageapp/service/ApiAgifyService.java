package com.janwojnar.nameageapp.service;

import com.janwojnar.nameageapp.web.adapter.ApiAgifyAdapter;
import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ApiAgifyService {

    private final ApiAgifyAdapter apiAgifyAdapter;

    public ApiAgifyResponse getNameData(String askedName) {
        return apiAgifyAdapter.getNameData(askedName);
    }
}
