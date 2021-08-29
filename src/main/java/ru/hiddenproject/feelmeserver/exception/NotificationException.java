package ru.hiddenproject.feelmeserver.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown if error occurs while sending notification
 * See {@link ru.hiddenproject.feelmeserver.service.NotificationService}
 */
public class NotificationException extends RequestException{

    public NotificationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public NotificationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
