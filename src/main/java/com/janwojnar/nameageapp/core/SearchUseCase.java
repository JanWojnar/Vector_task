package com.janwojnar.nameageapp.core;

import com.janwojnar.nameageapp.common.ErrorStatus;
import com.janwojnar.nameageapp.common.SortTyp;
import com.janwojnar.nameageapp.common.exception.BusinessLogicException;
import com.janwojnar.nameageapp.common.exception.TechnicalException;
import com.janwojnar.nameageapp.persistance.dao.SearchHistoryDao;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;
import com.janwojnar.nameageapp.service.ApiAgifyService;
import com.janwojnar.nameageapp.validator.SearchValidator;
import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.web.to.SearchHistoryResponse;
import com.janwojnar.nameageapp.web.to.SearchHistoryTo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchUseCase {

    private final SearchValidator searchValidator;
    private final ApiAgifyService apiAgifyService;
    private final SearchHistoryDao searchHistoryDao;

    public ApiAgifyResponse searchForNameData(String askedName) throws IOException, InterruptedException {
        validateName(askedName);
        ApiAgifyResponse nameData = this.apiAgifyService.getNameData(askedName);
        this.searchHistoryDao.addRecord(nameData);
        return nameData;
    }

    private void validateName(String askedName) {
        if (!this.searchValidator.validateName(askedName)) {
            throw new BusinessLogicException(HttpStatus.BAD_REQUEST, ErrorStatus.builder().errorMessages(Set.of(
                    "Given name has forbidden special signs!")).build());
        }
    }

    public SearchHistoryResponse getSearchHistory(Boolean sorted, Character typ) {
        SortTyp sortTyp = validateSearchHistoryEndpointInput(sorted, typ);
        List<SearchHistoryTo> records = getSearchHistoryData();
        sortIfNeccessary(sorted, sortTyp, records);
        return SearchHistoryResponse.builder().searchHistorySet(records).build();
    }

    private SortTyp validateSearchHistoryEndpointInput(Boolean sorted, Character typ) {
        SortTyp sortTyp;
        try {
            sortTyp = this.searchValidator.validateSearchHistoryEndpointInput(sorted, typ);
        } catch (IllegalAccessException e) {
            throw new BusinessLogicException(HttpStatus.BAD_REQUEST, ErrorStatus.builder().errorMessages
                    (Set.of(
                            "Given parameter 'sortTyp' was not recognized! Available: 'a' (age) and 'n' (name)")).build());
        }
        return sortTyp;
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

    private static void sortIfNeccessary(Boolean sorted, SortTyp sortTyp, List<SearchHistoryTo> records) {
        if (sorted) {
            if (sortTyp.equals(SortTyp.NAME)) {
                records.sort((search1, search2) -> search1.getName().compareToIgnoreCase(search2.getName()));
            } else {
                records.sort(Comparator.comparingInt(SearchHistoryTo::getAge));
            }
        }
    }
}

