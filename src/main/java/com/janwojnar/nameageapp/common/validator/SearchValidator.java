package com.janwojnar.nameageapp.common.validator;

import com.janwojnar.nameageapp.common.SortTyp;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Class that validates integrity of incoming Request.
 */
@Component
@Slf4j
public class SearchValidator {

    private final String justLettersRegex = "^[a-zA-ZĄąĘęĆćŻŹźżŚśÓóŃńŁł]+$";

    /**
     * Validates name with 'justLettersRegex'.
     *
     * @param name validated name.
     * @return validation outcome.
     */
    public boolean validateName(String name) {
        log.debug("Validation of name: " + name);
        return name.matches(this.justLettersRegex);
    }

    /**
     * @param sorted check whether sorting is required
     * @param typ    incoming sorting-type character.
     * @return Parsed enum SortTyp or null if not necessary
     * @throws IllegalAccessException when it is not possible to create Enum of incoming sorting type.
     */
    public SortTyp validateSearchHistoryEndpointInput(Boolean sorted, Character typ) throws IllegalAccessException {
        if (sorted) {
            log.debug("Validation of given sortTyp=" + typ);
            return SortTyp.of(typ);
        }
        return null;
    }

    /**
     * Interprets whether name was found in Agify API.
     *
     * @param nameData incoming AgifyApi response.
     * @return Interpretation outcome.
     */
    public boolean isNameRecognisedByAgify(ApiAgifyResponse nameData) {
        return nameData.getAge() != null;
    }
}
