package com.janwojnar.nameageapp.service;

import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.communication.to.SearchHistoryResponse;

public interface SearchService {

    /**
     * Method for searching in external API and saving record of search history.
     *
     * @param name searched name.
     * @return response of external API.
     */
    ApiAgifyResponse searchForNameData(String name);

    /**
     * Method returns search history of successful external API requests.
     *
     * @param sorted switches sorting (true / false)
     * @param typ    optional type of sorting ('a' - age, 'n' - name)s.
     * @return search history.
     */
    SearchHistoryResponse getSearchHistory(Boolean sorted, Character typ);
}
