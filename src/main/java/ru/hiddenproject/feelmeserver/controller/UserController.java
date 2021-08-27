package ru.hiddenproject.feelmeserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hiddenproject.feelmeserver.dto.*;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static ru.hiddenproject.feelmeserver.Url.*;

@RestController
@RequestMapping(API_PATH + USER.ENDPOINT)
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(USER.REGISTER)
    public ResponseEntity<ResponseDto<RegisteredUserDto>> register(
            @RequestBody @Valid BaseUserDto baseUserDto)
            throws DataValidityException, InternalException, DataExistsException {
        User user = userService.createUser(baseUserDto);
        RegisteredUserDto registeredUserDto = UserMapper.INSTANCE.modelToRegistered(user);
        return ResponseEntity.ok(
                new ResponseDto<>("", registeredUserDto)
        );
    }

    @PostMapping(USER.INVITE)
    public ResponseEntity<ResponseDto<String>> invite(
            @RequestBody @Valid BaseRequestDto<String> inviteRequest
            ) throws DataExistsException, ConstraintViolationException{

        User originalUser = userService.findByToken(inviteRequest.getToken());
        if(originalUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto<>("Wrong token")
            );
        }

        User acceptedUser = userService.findByCode(inviteRequest.getObject());
        if(acceptedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponseDto<>("User not found")
            );
        }

        AcceptedUser invitation = userService.inviteUser(originalUser, acceptedUser);
        return ResponseEntity.ok(
                new ResponseDto<>("", invitation.getInvitationStatus().name())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleDataExistsException(DataExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(DataValidityException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleDataValidityException(DataValidityException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleInternalException(InternalException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

}
