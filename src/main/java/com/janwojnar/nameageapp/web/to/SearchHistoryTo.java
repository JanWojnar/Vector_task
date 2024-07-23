package com.janwojnar.nameageapp.web.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchHistoryTo {
    private String name;
    private int age;
}
