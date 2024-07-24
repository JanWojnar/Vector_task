package com.janwojnar.nameageapp.validator;

import com.janwojnar.nameageapp.common.SortTyp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchValidator {

    private final String justLettersRegex = "^[\\p{L}]+$";

    public boolean validateName(String askedName) {
        log.debug("Validation of name: " + askedName);
        return askedName.matches(this.justLettersRegex);
    }

    public SortTyp validateSearchHistoryEndpointInput(Boolean sorted, Character typ) throws IllegalAccessException {
        if (sorted) {
            log.debug("Validation of given sortTyp=" + typ);
            return SortTyp.of(typ);
        }
        return null;
    }
}
