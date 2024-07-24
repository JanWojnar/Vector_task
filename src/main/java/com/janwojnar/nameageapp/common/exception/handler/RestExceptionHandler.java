package com.janwojnar.nameageapp.common.exception.handler;

import com.janwojnar.nameageapp.common.JsonPrettifier;
import com.janwojnar.nameageapp.common.exception.BusinessLogicException;
import com.janwojnar.nameageapp.common.exception.TechnicalException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class for handling exceptions thrown during incoming Rest request.
 */
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

    private final JsonPrettifier jsonPrettifier;

    /**
     * Catches Business Exception and returns convenient Response Entity.
     *
     * @param exception captured Exception.
     * @return custom ResponseEntity.
     */
    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<String> handleBusinessException(BusinessLogicException exception) {
        return new ResponseEntity<>(this.jsonPrettifier.createPrettyJson(exception.getErrorStatus()),
                exception.getHttpStatus());
    }

    /**
     * Catches Technical Exception and returns convenient Response Entity.
     *
     * @param exception captured Exception.
     * @return custom ResponseEntity.
     */
    @ExceptionHandler({TechnicalException.class})
    public ResponseEntity<String> handleBusinessException(TechnicalException exception) {
        return new ResponseEntity<>(this.jsonPrettifier.createPrettyJson(exception.getErrorStatus()),
                exception.getHttpStatus());
    }
}
