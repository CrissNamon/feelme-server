package ru.hiddenproject.feelmeserver.exception;

/**
 * Thrown if data is already exists
 */
public class DataExistsException extends Exception{

    public DataExistsException(String message) {
        super(message);
    }
}
