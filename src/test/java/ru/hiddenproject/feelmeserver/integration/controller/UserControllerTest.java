package ru.hiddenproject.feelmeserver.integration.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.hiddenproject.feelmeserver.dto.BaseRequestDto;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.dto.RegisteredUserDto;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.service.impl.UserServiceImpl;
import ru.hiddenproject.feelmeserver.integration.IntegrationTest;

import java.lang.reflect.Type;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hiddenproject.feelmeserver.Url.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    private String code;

    private String token;

    @Test
    public void registerInvalidDto() throws Exception {
        BaseUserDto baseUserDto = new BaseUserDto();
        String json = new Gson().toJson(baseUserDto);
        mockMvc.perform(
                post(API_PATH + USER.ENDPOINT + USER.REGISTER)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        status().isBadRequest()
                );
    }

    @Test
    public void registerValidUser() throws Exception{

        String login = "TestLogin";
        String deviceUID = "TestUID";

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setLogin(login);
        baseUserDto.setDeviceUID(deviceUID);
        String json = new Gson().toJson(baseUserDto);

        ResultActions resultActions = mockMvc.perform(
                post(
                     API_PATH + USER.ENDPOINT + USER.REGISTER
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.object.login").value(login)
                )
                .andExpect(
                        jsonPath("$.object.code").isNotEmpty()
                )
                .andExpect(
                        jsonPath("$.object.token").isNotEmpty()
                );
    }

    @Test
    public void registerExistedUser() throws Exception{
        String login = "TestLogin";
        String deviceUID = "TestUID";

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setLogin(login);
        baseUserDto.setDeviceUID(deviceUID);

        String json = new Gson().toJson(baseUserDto);

        mockMvc.perform(
                post(
                        API_PATH + USER.ENDPOINT + USER.REGISTER
                )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(
                        status().isConflict()
                );
    }

    @Test
    public void inviteUser() throws Exception {
        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setDeviceUID("TestUID1");
        baseUserDto.setLogin("TestLogin1");
        User user = userService.createUser(baseUserDto);
        token = user.getToken();

        baseUserDto.setDeviceUID("TestUID2");
        baseUserDto.setLogin("TestLogin2");
        user = userService.createUser(baseUserDto);
        code = user.getCode();

        BaseRequestDto<String> inviteRequest = new BaseRequestDto<>();
        inviteRequest.setToken(token);
        inviteRequest.setObject(code);
        String json = new Gson().toJson(inviteRequest);

        ResultActions resultActions = mockMvc.perform(
                post(API_PATH + USER.ENDPOINT + USER.INVITE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.object").value(InvitationStatus.PENDING.name())
                );
    }

}
