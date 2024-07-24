package com.janwojnar.nameageapp.validator;

import com.janwojnar.nameageapp.common.SortTyp;
import com.janwojnar.nameageapp.common.validator.SearchValidator;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SearchValidatorTest {
    SearchValidator searchValidator = new SearchValidator();

    private static Stream<Character> notRecognizableCharacters() {
        return Stream.of('x', 'y', 'z', null);
    }

    private static Stream<ApiAgifyResponse> agifyResponseTypes() {
        return Stream.of(
                ApiAgifyResponse.builder().name("Stan").age(20).build(),
                ApiAgifyResponse.builder().name("asfdasfdasdf").age(null).build());
    }

    @Test
    void validateNameShouldReturnTrueWithCorrectName() {
        //given
        String correctName = "Grażyna";
        //when
        boolean validated = this.searchValidator.validateName(correctName);
        //then
        assertTrue(validated);
    }

    @Test
    void validateNameShouldReturnFalseWithForbiddenName() {
        //given
        String incorrectName = "Jac8675(&*)(*ek";
        //when
        boolean validated = this.searchValidator.validateName(incorrectName);
        //then
        assertFalse(validated);
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'A', 'n', 'N'})
    void validateSearchHistoryEndpointInputWithSortingAndCorrectChars(char input) throws IllegalAccessException {
        //given
        //when
        SortTyp sortTyp = this.searchValidator.validateSearchHistoryEndpointInput(true, input);
        //then
        assertTrue(sortTyp.equals(SortTyp.NAME) || sortTyp.equals(SortTyp.AGE));
    }

    @ParameterizedTest
    @MethodSource("notRecognizableCharacters")
    void validateSearchHistoryEndpointInputWithoutSortingAndAnyChar(Character input) throws IllegalAccessException {
        //given
        //when
        SortTyp sortTyp = this.searchValidator.validateSearchHistoryEndpointInput(false, input);
        //then
        assertNull(sortTyp);
    }

    @ParameterizedTest
    @MethodSource("notRecognizableCharacters")
    void validateSearchHistoryEndpointInputWithSortingAndBadChars(Character input) {
        //given
        //when
        IllegalAccessException exception = assertThrows(IllegalAccessException.class,
                () -> this.searchValidator.validateSearchHistoryEndpointInput(true, input));
        //then
        assertEquals("SortTyp was not recognized!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("agifyResponseTypes")
    void isNameRecognizedByAgify(ApiAgifyResponse response) {
        //given
        //when
        //then
        assertEquals(response.getAge() != null, this.searchValidator.isNameRecognisedByAgify(response));
    }


}