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
        String token = "c9oQ-ks-TRm9YuNq7HZpJN:APA91bEx-egwWTyWO3mRUXCY5AF0xjB5Jk6uJtygED8Z-P23jnoQCJjaeJ_MVgMnq3ZbHJ32p2W9nKXERW-nn8wivVO-XoPKv3QTf3-5vPSP8nA5H1yVEiE__t4gna43veiaZQR2H0sY";
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
