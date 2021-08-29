package ru.hiddenproject.feelmeserver.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.exception.NotificationException;
import ru.hiddenproject.feelmeserver.service.impl.NotificationServiceImpl;
import ru.hiddenproject.feelmeserver.unit.UnitTest;

@SpringBootTest
public class NotificationServiceTest extends UnitTest {

    @Autowired
    private NotificationServiceImpl notificationService;

    private ResponseDto<String> responseDto;

    @Test
    public void sendNotificationFirebase() {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSubject("Test subject");
        notificationDto.setContent("Test content");
        notificationDto.putData("testkey", "testvalue");
        String token = "";
        /*
        Assertions.assertDoesNotThrow(() -> responseDto = notificationService.send(notificationDto, token));
        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getObject());
        Assertions.assertEquals("OK", responseDto.getMessage());

         */
    }

    @Test
    public void sendNotificationEmptyToken() {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSubject("Test subject");
        notificationDto.setContent("Test content");
        notificationDto.putData("testkey", "testvalue");
        String token = "";

        Assertions.assertThrows(NotificationException.class, () -> responseDto = notificationService.send(notificationDto, token));
    }

}
