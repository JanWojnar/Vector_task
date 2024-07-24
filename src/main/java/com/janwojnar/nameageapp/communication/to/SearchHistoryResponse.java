package com.janwojnar.nameageapp.communication.to;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response object with search history.
 */
@Data
@Builder
public class SearchHistoryResponse {
    private List<SearchHistoryTo> searchHistoryList;
}