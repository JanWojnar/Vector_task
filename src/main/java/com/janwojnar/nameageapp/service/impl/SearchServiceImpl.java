package com.janwojnar.nameageapp.service.impl;

import com.janwojnar.nameageapp.common.ErrorStatus;
import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.common.SortTyp;
import com.janwojnar.nameageapp.common.exception.BusinessLogicException;
import com.janwojnar.nameageapp.common.exception.TechnicalException;
import com.janwojnar.nameageapp.common.validator.SearchValidator;
import com.janwojnar.nameageapp.communication.adapter.ApiAgifyAdapter;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.communication.to.SearchHistoryResponse;
import com.janwojnar.nameageapp.communication.to.SearchHistoryTo;
import com.janwojnar.nameageapp.persistance.dao.SearchHistoryDao;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import com.janwojnar.nameageapp.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final SearchValidator searchValidator;

    private final ApiAgifyAdapter apiAgifyAdapter;

    private final SearchHistoryDao searchHistoryDao;

    @Override
    public ApiAgifyResponse searchForNameData(String name) {
        validateName(name);
        ApiAgifyResponse nameData = this.apiAgifyAdapter.getNameData(name);
        if (this.searchValidator.isNameRecognisedByAgify(nameData)) {
            saveRecord(nameData);
        } else {
            log.warn(LogPreparer.prepareLog(
                    "Name was not recognized by external API and will not be saved in search history!"));
        }
        return nameData;
    }

    private void saveRecord(ApiAgifyResponse nameData) {
        try {
            this.searchHistoryDao.addRecord(nameData);
        } catch (IOException e) {
            throw new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorStatus.builder().errorMessages(Set.of(
                            LogPreparer.prepareLog("Problem with saving history record occurred",
                                    e.getMessage()))).build());
        }
    }

    private void validateName(String askedName) {
        if (!this.searchValidator.validateName(askedName)) {
            throw new BusinessLogicException(HttpStatus.BAD_REQUEST, ErrorStatus.builder().errorMessages(Set.of(
                    LogPreparer.prepareLog("Given name: ", askedName, " has forbidden special signs!"))).build());
        }
    }

    @Override
    public SearchHistoryResponse getSearchHistory(Boolean sorted, Character typ) {
        SortTyp sortTyp = validateSearchHistoryEndpointInput(sorted, typ);
        List<SearchHistoryTo> records = getSearchHistoryData();
        sortIfNecessary(sorted, sortTyp, records);
        return SearchHistoryResponse.builder().searchHistoryList(records).build();
    }

    private SortTyp validateSearchHistoryEndpointInput(Boolean sorted, Character typ) {
        if (sorted) {
            try {
                return this.searchValidator.validateSearchHistoryEndpointInput(sorted, typ);
            } catch (IllegalAccessException e) {
                throw new BusinessLogicException(HttpStatus.BAD_REQUEST, ErrorStatus.builder().errorMessages
                        (Set.of(
                                "Given parameter 'sortTyp' was not recognized! Available: 'a' (age) and 'n' (name)")).build());
            }
        }
        return null;
    }

    private List<SearchHistoryTo> getSearchHistoryData() {
        List<SearchHistoryTo> records;
        try {
            records = this.searchHistoryDao.read()
                    .stream()
                    .map(SearchHistoryEty::toTransferObject)
                    .collect(Collectors.toList());
        } catch (InterruptedException | IOException e) {
            throw new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorStatus.builder().errorMessages(Set.of(e.getMessage())).build());
        }
        return records;
    }

    private void sortIfNecessary(Boolean sorted, SortTyp sortTyp, List<SearchHistoryTo> records) {
        if (sorted) {
            if (sortTyp.equals(SortTyp.NAME)) {
                records.sort((search1, search2) -> search1.getName().compareToIgnoreCase(search2.getName()));
            } else {
                records.sort(Comparator.comparingInt(SearchHistoryTo::getAge));
            }
        }
    }
}

