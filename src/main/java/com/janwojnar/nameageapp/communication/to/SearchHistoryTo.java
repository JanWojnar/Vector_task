package com.janwojnar.nameageapp.communication.to;

import lombok.Builder;
import lombok.Data;

/**
 * Transfer object with one record of search history.
 */
@Data
@Builder
public class SearchHistoryTo {
    private String name;
    private Integer age;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SearchHistoryTo other = (SearchHistoryTo) obj;
        return this.name.equals(other.name) && this.age.equals(other.age);
    }
}
