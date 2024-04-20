package ru.urfu.mm.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.urfu.mm.controller.authentication.LoginDTO;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthenticationControllerTest {
    @Mock
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        LoginDTO loginDTO = new LoginDTO(UUID.randomUUID().toString(), "123123123");
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(loginDTO);

        mockMvc
                .perform(post("/api/authentication/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
}
