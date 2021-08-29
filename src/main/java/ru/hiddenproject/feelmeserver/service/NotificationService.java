package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.exception.NotificationException;

/**
 * Interface for operations with notifications
 * @param <R> Response data type of response after sending
 */
public interface NotificationService<R> {

    /**
     * Sends notification with {@link ru.hiddenproject.feelmeserver.dto.NotificationDto} data to {@code receiver}
     * @param notificationDto Notification data <br> See {@link ru.hiddenproject.feelmeserver.dto.NotificationDto}
     * @param receiver Receiver of notification
     * @return Response after sending notification
     * @throws NotificationException If error occurs while send operation
     */
    ResponseDto<R> send(NotificationDto notificationDto, String receiver) throws NotificationException;

}
