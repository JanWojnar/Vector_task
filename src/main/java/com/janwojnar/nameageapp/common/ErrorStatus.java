package com.janwojnar.nameageapp.common;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * Collection of error messages.
 */
@Builder
@Getter
public class ErrorStatus {
    Set<String> errorMessages;
}
