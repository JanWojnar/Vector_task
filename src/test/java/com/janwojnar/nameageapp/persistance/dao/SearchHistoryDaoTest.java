package com.janwojnar.nameageapp.persistance.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import com.janwojnar.nameageapp.persistance.dao.impl.SearchHistoryDaoImpl;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchHistoryDaoTest {
    @Mock
    private DatabaseConfigurationProperties databaseConfigurationProperties;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private SearchHistoryDaoImpl searchHistoryDao;

    private Path tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        this.tempFilePath = Files.createTempFile("testfile", ".txt");
        when(this.databaseConfigurationProperties.getPath()).thenReturn(this.tempFilePath.toString());
    }

    @Test
    public void testAddRecord() throws IOException {
        ApiAgifyResponse apiAgifyResponse = ApiAgifyResponse.builder().age(27).name("Jan").build();

        when(this.mapper.writeValueAsString(any())).thenReturn("{\"name\":\"Jan\",\"age\": 27}");

        this.searchHistoryDao.addRecord(apiAgifyResponse);

        try (BufferedReader reader = new BufferedReader(new FileReader(this.tempFilePath.toFile()))) {
            String line = reader.readLine();
            assertEquals("{\"name\":\"Jan\",\"age\": 27}", line);
        }
    }

    @Test
    public void testRead() throws IOException, InterruptedException {
        SearchHistoryEty searchHistoryEty = new SearchHistoryEty();
        String jsonString = "{\"name\":\"Jan\",\"age\": 27}";

        when(this.mapper.readValue(jsonString, SearchHistoryEty.class)).thenReturn(searchHistoryEty);

        try (FileWriter writer = new FileWriter(this.tempFilePath.toFile())) {
            writer.write(jsonString + "\n");
        }

        Set<SearchHistoryEty> records = this.searchHistoryDao.read();

        assertEquals(1, records.size());
        assertEquals(searchHistoryEty, records.iterator().next());
    }
}