package ru.hiddenproject.feelmeserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hiddenproject.feelmeserver.Url.*;
import ru.hiddenproject.feelmeserver.dto.BaseRequestDto;
import ru.hiddenproject.feelmeserver.dto.ErrorResponseDto;
import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.NotificationException;
import ru.hiddenproject.feelmeserver.model.Invitation;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.NotificationService;
import ru.hiddenproject.feelmeserver.service.UserService;

import javax.validation.constraints.NotBlank;

import static ru.hiddenproject.feelmeserver.Url.API_PATH;

/**
 * Controller for notification actions
 * <br>
 * See {@link ru.hiddenproject.feelmeserver.service.NotificationService}
 */
@RestController
@RequestMapping(API_PATH + NOTIFICATION.ENDPOINT)
@Validated
public class NotificationController {

    private NotificationService<String> notificationService;

    private UserService userService;

    @Autowired
    public NotificationController(NotificationService<String> notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping(NOTIFICATION.SEND)
    public ResponseEntity<ResponseDto<String>> send(
            @RequestBody BaseRequestDto<String> notificationData)
    throws NotificationException{
        User sender = userService.findByToken(notificationData.getToken());
        if(sender == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto<>("Wrong token")
            );
        }
        User receiver = userService.findByCode(notificationData.getObject());
        if(receiver == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto<>("Receiver not found")
            );
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setContent(sender.getLogin() + " sent feeling to you");
        notificationDto.setSubject("FeelMe");
        ResponseDto<String> sendingResponse = notificationService.send(notificationDto, receiver.getFirebaseToken());
        return ResponseEntity.ok(sendingResponse);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleNotificationException(NotificationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

}
