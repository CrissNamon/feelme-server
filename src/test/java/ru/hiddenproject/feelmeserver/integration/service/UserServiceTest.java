package ru.hiddenproject.feelmeserver.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.integration.IntegrationTest;
import ru.hiddenproject.feelmeserver.model.Invitation;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.impl.InvitationServiceImpl;
import ru.hiddenproject.feelmeserver.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends IntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private InvitationServiceImpl acceptedUserService;

    private User user;

    @Test
    public void saveInvalidUser() {
        user = new User();
        Assertions.assertThrows(
                DataValidityException.class,
                () -> userService.save(user)
        );
    }

    @Test
    public void saveValidUser() {
        String login = "TestLogin";
        String deviceUID = "TestUID";
        user = new User();
        user.setLogin(login);
        user.setDeviceUID(deviceUID);
        Assertions.assertDoesNotThrow(() -> user = userService.save(user));
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(login, user.getLogin());
        Assertions.assertEquals(deviceUID, user.getDeviceUID());
    }

    @Test
    public void createInvalidUser() {
        BaseUserDto baseUserDto = new BaseUserDto();
        Assertions.assertThrows(
                DataValidityException.class,
                () -> userService.createUser(baseUserDto)
        );
    }

    @Test
    public void createValidUser() {
        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setDeviceUID("UID");
        baseUserDto.setLogin("LOGIN");
        Assertions.assertDoesNotThrow(() -> user = userService.createUser(baseUserDto));
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getCode());
        Assertions.assertNotNull(user.getToken());
    }

    @Test
    public void getEmptyInvitations() {
        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setDeviceUID("UID_INVITATION_EMPTY_TEST");
        baseUserDto.setLogin("LOGIN");
        Assertions.assertDoesNotThrow(() -> user = userService.createUser(baseUserDto));

        List<Invitation> invitationsExpected = new ArrayList<>();
        Assertions.assertEquals(invitationsExpected, acceptedUserService.getAllPendingInvitations(user.getId()));
    }

}
