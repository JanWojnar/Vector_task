package com.janwojnar.nameageapp.persistance.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import com.janwojnar.nameageapp.persistance.dao.SearchHistoryDao;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class SearchHistoryDaoImpl implements SearchHistoryDao {

    private final DatabaseConfigurationProperties databaseConfigurationProperties;

    private final ObjectMapper mapper;

    @Override
    public synchronized void addRecord(ApiAgifyResponse apiAgifyResponse) throws IOException {
        SearchHistoryEty searchHistoryEty = SearchHistoryEty.of(apiAgifyResponse);
        try (FileWriter writer = new FileWriter(this.databaseConfigurationProperties.getPath(), true)) {
            String dataBlock = this.mapper.writeValueAsString(searchHistoryEty);
            writer.append(dataBlock).append("\n");
        }
    }

    @Override
    public synchronized Set<SearchHistoryEty> read() throws IOException {
        Set<SearchHistoryEty> records = new HashSet<>();
        FileReader reader = new FileReader(this.databaseConfigurationProperties.getPath());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            records.add(this.mapper.readValue(line, SearchHistoryEty.class));
        }
        return records;
    }
}
