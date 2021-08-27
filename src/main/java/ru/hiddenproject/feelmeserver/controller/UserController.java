package ru.hiddenproject.feelmeserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hiddenproject.feelmeserver.dto.*;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static ru.hiddenproject.feelmeserver.Url.API_PATH;
import static ru.hiddenproject.feelmeserver.Url.USER;

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
    public ResponseEntity<ResponseDto<Long>> invite(
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
                new ResponseDto<>("", invitation.getId())
        );
    }

    @PostMapping(USER.ACCEPT)
    public ResponseEntity<ResponseDto<String>> accept(
            @RequestBody BaseRequestDto<Long> invitationId
    ) throws DataNotExistsException, DataExistsException {
        AcceptedUser acceptedUser = userService.acceptInvitation(invitationId.getObject());
        return ResponseEntity.ok(
                new ResponseDto<>("", acceptedUser.getAcceptedUser().getLogin())
        );
    }

    @GetMapping(USER.PENDING_LIST)
    public ResponseEntity<ResponseDto<List<InvitationResponseDto>>> getPendingInvitations(
            @RequestBody @Valid BaseRequestDto<String> request
    ) {
        User originalUser = userService.findByToken(request.getToken());
        if(originalUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto<>("Wrong token")
            );
        }
        List<AcceptedUser> invitations = userService.getAllPendingInvitations(originalUser.getId());
        List<InvitationResponseDto> responseDtos = invitations.stream().map(acceptedUser -> {
            InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
            invitationResponseDto.setId(acceptedUser.getId());
            invitationResponseDto.setLogin(acceptedUser.getAcceptedUser().getLogin());
            return invitationResponseDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(
                new ResponseDto<>("", responseDtos)
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

    @ExceptionHandler(DataNotExistsException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleDataNotExistsException(DataNotExistsException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

}
