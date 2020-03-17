package file.univ_playground.controller;

import file.univ_playground.domain.User;
import file.univ_playground.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Slf4j
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getAll() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .email("parksj914@naver.com")
                .nickName("WhiteOwl")
                .password("RJScnr1533")
                .name("박성주")
                .age(27)
                .level(50L)
                .build());

        given(userService.getUsers()).willReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("WhiteOwl")))
                .andDo(print());
    }

    @Test
    public void getUser() throws Exception {
        String email = "parksj914@naver.com";
        String nickName = "WhiteOwl";
        String password = "RJScnr1533";
        String name = "박성주";
        Integer age = 27;

        User user = User.builder()
                .id(1L)
                .level(50L)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();

        given(userService.getUser(eq(1L))).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("WhiteOwl")))
                .andDo(print());

    }

    @Test
    public void create() throws Exception {
        String email = "parksj914@naver.com";
        String nickName = "WhiteOwl";
        String password = "RJScnr1533";
        String name = "박성주";
        Integer age = 27;
        Long level = 50L;

        User user = User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .level(level)
                .build();

        given(userService.addUser(email, password, nickName, name, age, level))
                .willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"parksj914@naver.com\",\n" +
                        "  \"password\": \"RJScnr1533\",\n" +
                        "  \"nickName\": \"WhiteOwl\",\n" +
                        "  \"name\": \"박성주\",\n" +
                        "  \"age\": 27\n" +
                        "}"))
                .andExpect(status().isCreated());
        verify(userService).addUser(email, password, nickName, name, age, level);
    }

    @Test
    public void updatePassword() throws Exception {
        Long id = 1L;
        String password = "RJScnr0329";

        mockMvc.perform(MockMvcRequestBuilders
                .patch("/users/"+ id +"/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"password\": \"RJScnr0329\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).updatePassword(id, password);
    }

    @Test
    public void updateInformation() throws Exception {

        Long id = 1004L;
        String nickName = "WhiteMonkey";
        String name = "성주";
        String job = "development";
        String phoneNumber = "010-3925-1533";
        String hobby = "Reading book";

        mockMvc.perform(MockMvcRequestBuilders
                .patch("/users/"+id+"/information")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"nickName\": \"WhiteMonkey\",\n" +
                        "  \"name\": \"성주\",\n" +
                        "  \"job\": \"development\",\n" +
                        "  \"phoneNumber\": \"010-3925-1533\",\n" +
                        "  \"hobby\": \"Reading book\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).updateInformation(
                id,nickName,name,job,phoneNumber,hobby);
    }

    @Test
    public void deactivate() throws Exception {
        Long id = 1004L;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/" + id))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).deactiveUser(id);
    }

}
