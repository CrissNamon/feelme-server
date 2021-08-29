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
import ru.hiddenproject.feelmeserver.model.Invitation;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.InvitationService;
import ru.hiddenproject.feelmeserver.service.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static ru.hiddenproject.feelmeserver.Url.API_PATH;
import static ru.hiddenproject.feelmeserver.Url.USER;

/**
 * Controller for user actions
 * <br>
 * See {@link ru.hiddenproject.feelmeserver.model.User}, {@link Invitation}
 */
@RestController
@RequestMapping(API_PATH + USER.ENDPOINT)
@Validated
public class UserController {

    private final UserService userService;

    private final InvitationService invitationService;

    @Autowired
    public UserController(UserService userService, InvitationService invitationService) {
        this.userService = userService;
        this.invitationService = invitationService;
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
            ) throws DataExistsException, ConstraintViolationException, DataNotExistsException{

        User originalUser = userService.findByToken(inviteRequest.getToken());
        User acceptedUser = userService.findByCode(inviteRequest.getObject());

        Invitation invitation = invitationService.inviteUser(originalUser, acceptedUser);
        return ResponseEntity.ok(
                new ResponseDto<>("", invitation.getAcceptedUser().getCode())
        );
    }

    @PostMapping(USER.ACCEPT)
    public ResponseEntity<ResponseDto<String>> accept(
            @RequestBody BaseRequestDto<Long> invitationId
    ) throws DataNotExistsException, DataExistsException {
        Invitation invitation = invitationService.acceptInvitation(invitationId.getObject());
        return ResponseEntity.ok(
                new ResponseDto<>("", invitation.getInvitationStatus().name())
        );
    }

    @PostMapping(USER.REJECT)
    public ResponseEntity<ResponseDto<Boolean>> reject(
            @RequestBody BaseRequestDto<Long> invitationId
    ) throws DataNotExistsException, DataExistsException {
        invitationService.rejectInvitation(invitationId.getObject());
        return ResponseEntity.ok(
                new ResponseDto<>("", true)
        );
    }

    @GetMapping(USER.PENDING_LIST)
    public ResponseEntity<ResponseDto<List<InvitationResponseDto>>> getPendingInvitations(
            @RequestBody @Valid BaseRequestDto<String> request
    ) throws DataNotExistsException{
        User originalUser = userService.findByToken(request.getToken());

        List<Invitation> invitations = invitationService.getAllPendingInvitations(originalUser.getId());
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

    @GetMapping(USER.STATUS)
    public ResponseEntity<ResponseDto<InvitationResponseDto>> getInvitationStatus(
            @RequestBody @Valid BaseRequestDto<Long> request) throws DataNotExistsException {
        Invitation invitation = invitationService.getInvitation(request.getObject());
        User originalUser = userService.findByToken(request.getToken());
        if(originalUser.getId() != invitation.getOriginalUser().getId()
        && originalUser.getId() != invitation.getAcceptedUser().getId()) {
            throw new DataNotExistsException("Given invitation is not yours!", HttpStatus.FORBIDDEN);
        }
        InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
        invitationResponseDto.setLogin(invitation.getAcceptedUser().getLogin());
        invitationResponseDto.setId(invitation.getId());
        invitationResponseDto.setStatus(invitation.getInvitationStatus());
        return ResponseEntity.ok(
                new ResponseDto<>("", invitationResponseDto)
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
        return ResponseEntity.status(e.getResponseStatus())
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(DataValidityException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleDataValidityException(DataValidityException e) {
        return ResponseEntity.status(e.getResponseStatus())
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleInternalException(InternalException e) {
        return ResponseEntity.status(e.getResponseStatus())
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

    @ExceptionHandler(DataNotExistsException.class)
    public ResponseEntity<ErrorResponseDto<Exception>> handleDataNotExistsException(DataNotExistsException e) {
        return ResponseEntity.status(e.getResponseStatus())
                .body(
                        new ErrorResponseDto<>(e.getMessage())
                );
    }

}
