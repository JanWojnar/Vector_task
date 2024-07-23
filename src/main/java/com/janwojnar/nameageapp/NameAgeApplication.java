package com.janwojnar.nameageapp;

import com.janwojnar.nameageapp.persistance.PersistanceConfig;
import com.janwojnar.nameageapp.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import({WebConfig.class, PersistanceConfig.class})
@SpringBootApplication
@EnableConfigurationProperties
public class NameAgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NameAgeApplication.class, args);
    }

}
