package ru.hiddenproject.feelmeserver.exception;

/**
 * Thrown if data is not exists
 */
public class DataNotExistsException extends Exception{

    public DataNotExistsException(String message) {
        super(message);
    }
}
