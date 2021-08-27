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
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.integration.Impl.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends IntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

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

}