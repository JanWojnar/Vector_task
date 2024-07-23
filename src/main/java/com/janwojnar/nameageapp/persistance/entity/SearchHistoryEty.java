package com.janwojnar.nameageapp.persistance.entity;

import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.web.to.SearchHistoryTo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryEty implements Serializable {

    private String name;

    private int age;

    public static SearchHistoryEty of(ApiAgifyResponse apiAgifyResponse) {
        return SearchHistoryEty.builder().name(apiAgifyResponse.getName()).age(apiAgifyResponse.getAge()).build();
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
}
