package com.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.model.User;
import com.wallet.model.dto.UserDTO;
import com.wallet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    private static final Long   ID        = 1L;
    private static final String EMAIL     = "email@teste.com";
    private static final String NAME      = "User test";
    private static final String PASSWORD  = "123456";
    private static final String URL       = "/user";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void testSave() throws Exception {
        BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJasonPayLoadUser(ID,NAME,EMAIL,PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    public void testeSaveInvalidUser() throws Exception {
        BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJasonPayLoadUser(ID,NAME,"email",PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Email inválido!"));
    }

    public User getMockUser(){
        return User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public String getJasonPayLoadUser(Long id, String nome, String email, String password) throws JsonProcessingException {
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .name(nome)
                .email(email)
                .password(password)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(userDTO);
    }
}
