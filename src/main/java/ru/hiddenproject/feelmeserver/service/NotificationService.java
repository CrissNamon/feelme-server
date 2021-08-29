package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.exception.NotificationException;

public interface NotificationService {

    void send(NotificationDto notificationDto) throws NotificationException;

}
