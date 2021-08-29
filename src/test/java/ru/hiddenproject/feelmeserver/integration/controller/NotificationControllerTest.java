package ru.hiddenproject.feelmeserver.integration.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hiddenproject.feelmeserver.dto.BaseRequestDto;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.integration.IntegrationTest;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.service.impl.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hiddenproject.feelmeserver.Url.API_PATH;
import static ru.hiddenproject.feelmeserver.Url.NOTIFICATION;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class NotificationControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private String token;

    private String code;

    @BeforeEach
    public void populate() throws Exception {
        userRepository.deleteAll();

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setLogin("Test");
        baseUserDto.setDeviceUID("TestUID");
        User original = userService.createUser(baseUserDto);
        token = original.getToken();

        baseUserDto.setLogin("Test2");
        baseUserDto.setDeviceUID("TestUID2");
        User receiver = userService.createUser(baseUserDto);
        code = receiver.getCode();
    }

    @Test
    public void sendNotificationWrongToken() throws Exception {
        BaseRequestDto<String> notificationData = new BaseRequestDto<>();
        notificationData.setObject("");

        String json = new Gson().toJson(notificationData);
        mockMvc.perform(
                post(API_PATH + NOTIFICATION.ENDPOINT + NOTIFICATION.SEND)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        status().isBadRequest()
                );
    }

    @Test
    public void sendNotificationWrongReceiver() throws Exception {
        BaseRequestDto<String> notificationData = new BaseRequestDto<>();
        notificationData.setToken(token);
        notificationData.setObject("");

        String json = new Gson().toJson(notificationData);
        mockMvc.perform(
                post(API_PATH + NOTIFICATION.ENDPOINT + NOTIFICATION.SEND)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        status().isBadRequest()
                );
    }
}
