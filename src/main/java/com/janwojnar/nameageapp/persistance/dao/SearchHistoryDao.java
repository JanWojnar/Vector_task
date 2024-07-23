package com.janwojnar.nameageapp.persistance.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

@Repository
@AllArgsConstructor
public class SearchHistoryDao {

    private final DatabaseConfigurationProperties databaseConfigurationProperties;

    private final ObjectMapper mapper;
    private final Semaphore fileLock = new Semaphore(1);

    public synchronized void addRecord(ApiAgifyResponse apiAgifyResponse) throws InterruptedException, IOException {
        SearchHistoryEty searchHistoryEty = SearchHistoryEty.of(apiAgifyResponse);
        try (FileWriter writer = new FileWriter(this.databaseConfigurationProperties.getPath(), true)) {
            this.fileLock.acquire();
            String dataBlock = this.mapper.writeValueAsString(searchHistoryEty);
            writer.append(dataBlock).append("\n");
        } finally {
            this.fileLock.release();
        }
    }

    public synchronized Set<SearchHistoryEty> read() throws InterruptedException, IOException {
        Set<SearchHistoryEty> records = new HashSet<>();
        this.fileLock.acquire();
        try {
            FileReader reader = new FileReader(this.databaseConfigurationProperties.getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                records.add(this.mapper.readValue(line, SearchHistoryEty.class));
            }
        } finally {
            this.fileLock.release();
        }
        return records;
    }
}
