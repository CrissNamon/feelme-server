package ru.hiddenproject.feelmeserver.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown if data is not valid
 * <br>
 * See {@link ru.hiddenproject.feelmeserver.util.ValidationUtils}
 */
public class DataValidityException extends RequestException{
    public DataValidityException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DataValidityException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
