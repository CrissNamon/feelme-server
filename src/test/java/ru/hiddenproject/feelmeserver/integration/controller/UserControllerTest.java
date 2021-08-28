package ru.hiddenproject.feelmeserver.integration.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.hiddenproject.feelmeserver.dto.BaseRequestDto;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.dto.RegisteredUserDto;
import ru.hiddenproject.feelmeserver.dto.ResponseDto;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.AcceptedUserRepository;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AcceptedUserRepository acceptedUserRepository;

    private String token;

    private String code;

    @BeforeEach
    public void init() throws Exception{
        userRepository.deleteAll();
        acceptedUserRepository.deleteAll();

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setDeviceUID("TestUID");
        baseUserDto.setLogin("TestLogin");
        User user = userService.createUser(baseUserDto);
        token = user.getToken();

        baseUserDto = new BaseUserDto();
        baseUserDto.setDeviceUID("TestUID2");
        baseUserDto.setLogin("TestLogin2");
        user = userService.createUser(baseUserDto);
        code = user.getCode();
    }

    @Test
    @Order(1)
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
    @Order(2)
    public void registerValidUser() throws Exception{

        String login = "TestLogin1";
        String deviceUID = "TestUID1";

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
    @Order(3)
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
    @Order(4)
    public void inviteUser() throws Exception {
        BaseRequestDto<String> inviteRequest = new BaseRequestDto<>();
        inviteRequest.setToken(token);
        inviteRequest.setObject(code);
        String json = new Gson().toJson(inviteRequest);

        mockMvc.perform(
                post(API_PATH + USER.ENDPOINT + USER.INVITE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.object").isNumber()
                );
    }

    @Test
    @Order(5)
    public void acceptInvitation() throws Exception{

        User originalUser = userRepository.findByToken(token).orElse(null);
        User acceptedUser = userRepository.findByCode(code).orElse(null);
        Assertions.assertNotNull(originalUser);
        Assertions.assertNotNull(acceptedUser);

        AcceptedUser invitation = new AcceptedUser();
        invitation.setOriginalUser(originalUser);
        invitation.setAcceptedUser(acceptedUser);
        invitation.setInvitationStatus(InvitationStatus.PENDING);
        invitation = acceptedUserRepository.save(invitation);

        Assertions.assertNotNull(invitation);
        Assertions.assertNotNull(invitation.getId());

        BaseRequestDto<Long> inviteAcceptRequest = new BaseRequestDto<>();
        inviteAcceptRequest.setToken(token);
        inviteAcceptRequest.setObject(invitation.getId());
        String json = new Gson().toJson(inviteAcceptRequest);

        mockMvc.perform(
                post(API_PATH + USER.ENDPOINT + USER.ACCEPT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(
                        status().isOk()
                );
    }
}
