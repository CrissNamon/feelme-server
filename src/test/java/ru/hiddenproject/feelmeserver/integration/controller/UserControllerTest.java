package ru.hiddenproject.feelmeserver.integration.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.integration.Impl.UserServiceImpl;
import ru.hiddenproject.feelmeserver.integration.IntegrationTest;
import ru.hiddenproject.feelmeserver.util.StringUtils;

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

    @Test
    public void registerValidUser() throws Exception{

        String login = "TestLogin";
        String deviceUID = "TestUID";

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setLogin(login);
        baseUserDto.setDeviceUID(deviceUID);
        String json = StringUtils.toJson(baseUserDto);

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
    public void registerExistedUser() throws Exception{
        String login = "TestLogin";
        String deviceUID = "TestUID";

        BaseUserDto baseUserDto = new BaseUserDto();
        baseUserDto.setLogin(login);
        baseUserDto.setDeviceUID(deviceUID);

        String json = StringUtils.toJson(baseUserDto);

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

}
