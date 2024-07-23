package com.janwojnar.nameageapp.common.exception.handler;

import com.janwojnar.nameageapp.common.JsonPrettifier;
import com.janwojnar.nameageapp.common.exception.BusinessLogicException;
import com.janwojnar.nameageapp.common.exception.TechnicalException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler {

    private final JsonPrettifier jsonPrettifier;

    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<String> handleBusinessException(BusinessLogicException exception) {
        return new ResponseEntity<>(this.jsonPrettifier.createPrettyJson(exception.getErrorStatus()),
                exception.getHttpStatus());
    }

    @ExceptionHandler({TechnicalException.class})
    public ResponseEntity<String> handleBusinessException(TechnicalException exception) {
        return new ResponseEntity<>(this.jsonPrettifier.createPrettyJson(exception.getErrorStatus()),
                exception.getHttpStatus());
    }
}
