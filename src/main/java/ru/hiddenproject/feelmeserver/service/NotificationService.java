package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.exception.NotificationException;

public interface NotificationService<R> {

    ResponseDto<R> send(NotificationDto notificationDto, String receiver) throws NotificationException;

}
