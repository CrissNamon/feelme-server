package ru.hiddenproject.feelmeserver.exception;

/**
 * Thrown if error occurs while sending notification
 * See {@link ru.hiddenproject.feelmeserver.service.NotificationService}
 */
public class NotificationException extends Exception{

    public NotificationException(String message) {
        super(message);
    }
}
