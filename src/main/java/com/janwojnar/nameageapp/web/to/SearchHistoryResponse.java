package com.janwojnar.nameageapp.web.to;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchHistoryResponse {
    private List<SearchHistoryTo> searchHistorySet;
}