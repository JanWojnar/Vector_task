package com.janwojnar.nameageapp.persistance.entity;

import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.communication.to.SearchHistoryTo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of 'entity' that is saved/read from text 'database'.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryEty implements Serializable {

    private String name;

    private Integer age;

    public static SearchHistoryEty of(ApiAgifyResponse apiAgifyResponse) {
        return SearchHistoryEty.builder().name(apiAgifyResponse.getName().toLowerCase()).age(apiAgifyResponse.getAge()).build();
    }

    public SearchHistoryTo toTransferObject() {
        return SearchHistoryTo.builder().age(this.age).name(this.name).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SearchHistoryEty other = (SearchHistoryEty) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.age, this.name);
    }
}
