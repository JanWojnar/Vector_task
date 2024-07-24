package com.janwojnar.nameageapp.common.exception;

import com.janwojnar.nameageapp.common.ErrorStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Technical exception.
 */
@Getter
public class TechnicalException extends RuntimeException {

    ErrorStatus errorStatus;

    HttpStatus httpStatus;

    public TechnicalException(HttpStatus httpStatus, ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessages().toString());
        this.errorStatus = errorStatus;
        this.httpStatus = httpStatus;
    }
}
