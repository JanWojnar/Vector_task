package com.janwojnar.nameageapp.persistance.initialiser;

import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.common.ShutdownManager;
import com.janwojnar.nameageapp.common.SpringProfiles;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Creates database in given path from application.properties.
 */
@Component
@AllArgsConstructor
@Slf4j
public class DatabaseCreator implements ApplicationListener<ContextRefreshedEvent> {

    DatabaseConfigurationProperties databaseConfigurationProperties;

    Environment environment;

    ShutdownManager shutdownManager;

    /**
     * Creates database new empty or populated database depending on profile or uses already created when exists.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Path target = Paths.get(this.databaseConfigurationProperties.getPath());
        if (environment.matchesProfiles(SpringProfiles.DB_POPULATED)) {
            String resourcePath = "/database/populatedDb.txt";
            try (InputStream resourceStream = getClass().getResourceAsStream(resourcePath)) {
                assert resourceStream != null;
                Files.copy(resourceStream, target);
                logDatabaseCreated();
            } catch (IOException e) {
                if (!(e instanceof FileAlreadyExistsException)) {
                    log.error(String.valueOf(e));
                    this.shutdownManager.initiateShutdown(1);
                }
                logDatabaseExists();
            }
        } else {
            try {
                Files.createFile(target);
                logDatabaseCreated();
            } catch (IOException e) {
                if (!(e instanceof FileAlreadyExistsException)) {
                    log.error(String.valueOf(e));
                    this.shutdownManager.initiateShutdown(1);
                }
                logDatabaseExists();
            }
        }
    }

    private void logDatabaseExists() {
        log.debug(LogPreparer.prepareLog("Database under path: \"", this.databaseConfigurationProperties.getPath(),
                "\" already exists and will be used."));
    }

    private void logDatabaseCreated() {
        log.debug(LogPreparer.prepareLog("Database under path: \"", this.databaseConfigurationProperties.getPath(),
                "\" created."));
    }
}
