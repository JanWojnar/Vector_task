package com.janwojnar.nameageapp.persistance.dao;

import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import com.janwojnar.nameageapp.test.TestDatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class SearchHistoryDaoIntegrationTest {

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @Autowired
    DatabaseConfigurationProperties databaseConfigurationProperties;

    @BeforeEach
    void prepare() throws IOException {
        TestDatabaseManager.emptyDatabase(this.databaseConfigurationProperties.getPath());
    }

    @AfterEach
    void tearDown() throws IOException {
        TestDatabaseManager.emptyDatabase(this.databaseConfigurationProperties.getPath());
    }

    @Test
    void testSearchForNameDataReturnsSearchHistoryIgnoringCase() throws IOException, InterruptedException {
        //given
        List<ApiAgifyResponse> records = List.of(
                ApiAgifyResponse.builder().name("KAROL").age(45).build(),
                ApiAgifyResponse.builder().name("KarOl").age(45).build(),
                ApiAgifyResponse.builder().name("karol").age(45).build(),
                ApiAgifyResponse.builder().name("KaRoL").age(45).build(),
                ApiAgifyResponse.builder().name("MAGDA").age(43).build(),
                ApiAgifyResponse.builder().name("magda").age(43).build(),
                ApiAgifyResponse.builder().name("mAGda").age(43).build(),
                ApiAgifyResponse.builder().name("MagdA").age(43).build());

        //when
        for (ApiAgifyResponse record : records) {
            this.searchHistoryDao.addRecord(record);
        }
        Set<SearchHistoryEty> searchHistories = this.searchHistoryDao.read();

        //then
        Assertions.assertAll(
                () -> assertEquals(2, searchHistories.size()),
                () -> assertTrue(searchHistories.stream().anyMatch(history -> history.getName().equals("karol"))),
                () -> assertTrue(searchHistories.stream().anyMatch(history -> history.getName().equals("magda")))
        );
    }
}