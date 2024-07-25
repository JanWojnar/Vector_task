package com.janwojnar.nameageapp.common;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShutdownManager {

    private final ConfigurableApplicationContext context;

    public void initiateShutdown(int returnCode) {
        SpringApplication.exit(context, () -> returnCode);
    }
}
