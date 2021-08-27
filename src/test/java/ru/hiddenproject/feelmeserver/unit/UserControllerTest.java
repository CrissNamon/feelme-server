package ru.hiddenproject.feelmeserver.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hiddenproject.feelmeserver.Url.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
