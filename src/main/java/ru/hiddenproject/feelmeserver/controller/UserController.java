package ru.hiddenproject.feelmeserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.dto.ErrorResponseDto;
import ru.hiddenproject.feelmeserver.dto.RegisteredUserDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import static ru.hiddenproject.feelmeserver.Url.*;

@RestController
@RequestMapping(API_PATH)
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(USER.ENDPOINT + USER.REGISTER)
    public ResponseEntity<ResponseDto<RegisteredUserDto>> register(
            @RequestBody @Valid BaseUserDto baseUserDto) throws DataValidityException, InternalException {
        User user = userService.createUser(baseUserDto);
        RegisteredUserDto registeredUserDto = UserMapper.INSTANCE.modelToRegistered(user);
        return ResponseEntity.ok(
                new ResponseDto<>("", registeredUserDto)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
