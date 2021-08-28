package ru.hiddenproject.feelmeserver.exception;

/**
 * Thrown if data is not valid
 * <br>
 * See {@link ru.hiddenproject.feelmeserver.util.ValidationUtils}
 */
public class DataValidityException extends Exception{
    public DataValidityException(String message) {
        super(message);
    }
}
