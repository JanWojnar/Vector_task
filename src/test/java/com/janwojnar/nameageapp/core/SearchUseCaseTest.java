package com.janwojnar.nameageapp.core;

import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.common.exception.BusinessLogicException;
import com.janwojnar.nameageapp.persistance.config.DatabaseConfigurationProperties;
import com.janwojnar.nameageapp.persistance.dao.SearchHistoryDao;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import com.janwojnar.nameageapp.test.TestDatabaseManager;
import com.janwojnar.nameageapp.web.adapter.ApiAgifyAdapter;
import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.web.to.SearchHistoryTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class SearchUseCaseTest {

    private static final String NAME_NOT_FOUND = "notfoundname";

    @Autowired
    SearchUseCase searchUseCase;

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @Autowired
    DatabaseConfigurationProperties databaseConfigurationProperties;

    @MockBean
    ApiAgifyAdapter apiAgifyAdapterMock;

    @BeforeEach
    void prepare() throws IOException {
        TestDatabaseManager.emptyDatabase(this.databaseConfigurationProperties.getPath());
    }

    @AfterEach
    void tearDown() throws IOException {
        TestDatabaseManager.emptyDatabase(this.databaseConfigurationProperties.getPath());
    }

    @Test
    void searchForNameDataTest() throws IOException, InterruptedException {
        //given
        List<String> mixedNames = List.of("Alina", "Halina", "Celina", "Grażyna", NAME_NOT_FOUND);
        List<String> expectedNames = mixedNames.stream().filter(name -> !name.equals(NAME_NOT_FOUND)).toList();
        mockAgifyApiResponse(mixedNames);

        //when
        for (String name : mixedNames) {
            this.searchUseCase.searchForNameData(name);
        }
        Set<SearchHistoryEty> savedRecords = this.searchHistoryDao.read();

        //then
        assertAll(
                () -> assertEquals(mixedNames.size() - 1, savedRecords.size()),
                () -> assertTrue(savedRecords.stream().allMatch(record -> record.getName() != null && record.getAge() != null)),
                () -> assertTrue(savedRecords.stream().map(SearchHistoryEty::getName).toList().containsAll(expectedNames)));
    }

    void mockAgifyApiResponse(List<String> names) {
        Random random = new Random();
        for (String name : names) {
            if (!name.equals(NAME_NOT_FOUND)) {
                when(this.apiAgifyAdapterMock.getNameData(name)).thenReturn(
                        ApiAgifyResponse.builder().name(name).age(random.nextInt(100) + 1).build());
            } else {
                when(this.apiAgifyAdapterMock.getNameData(NAME_NOT_FOUND)).thenReturn(ApiAgifyResponse.builder().name(
                        name).age(null).build());
            }
        }
    }

    @Test
    void getSearchHistoryIntegrationTestWithoutSorting() throws IOException, InterruptedException {
        //given
        TestDatabaseManager.populateDatabase(this.databaseConfigurationProperties.getPath());
        List<SearchHistoryEty> dataDirectFromDatabase = this.searchHistoryDao.read().stream().toList();
        //when
        List<SearchHistoryTo> searchHistoryList =
                this.searchUseCase.getSearchHistory(false, null).getSearchHistoryList();
        //then
        assertEquals(dataDirectFromDatabase.size(), searchHistoryList.size());
        assertAll(
                () -> assertEquals(dataDirectFromDatabase.size(), searchHistoryList.size()),
                () -> assertTrue(!isSortedByAge(searchHistoryList) && !isSortedAlphabetically(searchHistoryList))
        );
    }

    @Test
    void getSearchHistoryIntegrationTestWithSortingByAge() throws IOException, InterruptedException {
        //given
        TestDatabaseManager.populateDatabase(this.databaseConfigurationProperties.getPath());
        List<SearchHistoryEty> dataDirectFromDatabase = this.searchHistoryDao.read().stream().toList();
        //when
        List<SearchHistoryTo> searchHistoryList = this.searchUseCase.getSearchHistory(true, 'A').getSearchHistoryList();
        //then
        assertAll(
                () -> assertEquals(dataDirectFromDatabase.size(), searchHistoryList.size()),
                () -> assertTrue(isSortedByAge(searchHistoryList))
        );
    }

    @Test
    void getSearchHistoryIntegrationTestWithSortingByName() throws IOException, InterruptedException {
        //given
        TestDatabaseManager.populateDatabase(this.databaseConfigurationProperties.getPath());
        List<SearchHistoryEty> dataDirectFromDatabase = this.searchHistoryDao.read().stream().toList();
        //when
        List<SearchHistoryTo> searchHistoryList = this.searchUseCase.getSearchHistory(true, 'N').getSearchHistoryList();
        //then
        assertAll(
                () -> assertEquals(dataDirectFromDatabase.size(), searchHistoryList.size()),
                () -> assertTrue(isSortedAlphabetically(searchHistoryList))
        );
    }

    @Test
    void getSearchHistoryIntegrationTestWithUnknownSortingTyp() throws IOException, InterruptedException {
        //given
        TestDatabaseManager.populateDatabase(this.databaseConfigurationProperties.getPath());
        List<SearchHistoryEty> dataDirectFromDatabase = this.searchHistoryDao.read().stream().toList();
        //when
        BusinessLogicException exception = assertThrows(BusinessLogicException.class,
                () -> this.searchUseCase.getSearchHistory(true, 'X').getSearchHistoryList());
        //then
        assertEquals("[Given parameter 'sortTyp' was not recognized! Available: 'a' (age) and 'n' (name)]",
                exception.getMessage());
    }

    @Test
    void searchForNameDataWithForbiddenName() throws IOException, InterruptedException {
        //given
        TestDatabaseManager.populateDatabase(this.databaseConfigurationProperties.getPath());
        List<SearchHistoryEty> dataDirectFromDatabase = this.searchHistoryDao.read().stream().toList();
        String nameWithSpecialSigns = "Arczix&^%4$";
        String expectedExceptionMessage = LogPreparer.prepareLog("[", "Given name: ", nameWithSpecialSigns, " has " +
                "forbidden special signs!]");
        //when
        BusinessLogicException exception = assertThrows(BusinessLogicException.class,
                () -> this.searchUseCase.searchForNameData("Arczix&^%4$"));
        //then
        assertEquals(expectedExceptionMessage,
                exception.getMessage());
    }


    private boolean isSortedByAge(List<SearchHistoryTo> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getAge() > list.get(i + 1).getAge()) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedAlphabetically(List<SearchHistoryTo> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getName().compareTo(list.get(i + 1).getName()) > 0) {
                return false;
            }
        }
        return true;
    }
}