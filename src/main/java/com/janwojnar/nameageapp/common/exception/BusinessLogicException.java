package com.janwojnar.nameageapp.common.exception;

import com.janwojnar.nameageapp.common.ErrorStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessLogicException extends RuntimeException {

    ErrorStatus errorStatus;

    HttpStatus httpStatus;

    public BusinessLogicException(HttpStatus httpStatus, ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessages().toString());
        this.errorStatus = errorStatus;
        this.httpStatus = httpStatus;
    }
}
