package com.intuit.account.controller;

import static com.intuit.account.util.UserUtil.createUserDto;
import static com.intuit.account.util.UserUtil.createUserDtoWithId;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.account.AccountApplication;
import com.intuit.account.model.User;
import com.intuit.account.model.UserType;
import com.intuit.account.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AccountApplication.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetUserByUserId() throws Exception {
        User user = createUserDtoWithId();
        when(userService.findByUserId(any(String.class))).thenReturn(user);

        mockMvc.perform(get("/users/946517")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(user))));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user = createUserDtoWithId();
        when(userService.findAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(Arrays.asList(user)))));
    }

    @Test
    public void testGetUsersByType() throws Exception {
        User user = createUserDtoWithId();
        when(userService.findByUserType(any(UserType.class))).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users/type/INDIVIDUAL")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(Arrays.asList(user)))));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = createUserDto();
        when(userService.createUser(any(User.class))).thenReturn(createUserDtoWithId());

        mockMvc.perform(post("/users")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("requestedId")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = createUserDtoWithId();
        doNothing().when(userService).deleteUser(any(String.class));

        mockMvc.perform(delete("/users/" + user.getId())
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}