package ru.hiddenproject.feelmeserver.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.service.Impl.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@FlywayTest
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @Test
    public void saveInvalidUser() {
        user = new User();
        Assertions.assertThrows(
                DataValidityException.class,
                () -> userService.save(user)
        );
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
