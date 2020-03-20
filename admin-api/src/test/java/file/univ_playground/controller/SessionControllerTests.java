package file.univ_playground.controller;

import file.univ_playground.domain.User;
import file.univ_playground.exception.EmailNotExistedException;
import file.univ_playground.exception.PasswordWrongException;
import file.univ_playground.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //인증 성공 -> 토큰 줌
    @Test
    public void createWithValidAttributes() throws Exception {
        Long id = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;


        User mockUser = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();
        given(userService.authenticate(email,password))
                .willReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post(
                "/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"parksj914@naver.com\",\n" +
                        "  \"password\": \"RJScnr1533\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "location","/session"))
                .andExpect(content().string(
                        "" +
                                "{\"accessToken\":\"RJScnr1533\"}"))
                .andDo(print());
        verify(userService).authenticate(eq(email),eq(password));
    }

    //존재하지 않는 이메일 로그인
    @Test
    public void createWithNotExistedEmail() throws Exception {

        given(userService
                .authenticate("x@example.com","test"))
                .willThrow(EmailNotExistedException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"x@example.com\",\n" +
                        "  \"password\": \"test\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(userService).authenticate(
                        eq("x@example.com"),
                        eq("test"));
    }

    @Test
    public void createWrongPassword() throws Exception {
        given(userService.authenticate(
                "tester@example.com","x"))
                .willThrow(PasswordWrongException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"tester@example.com\",\n" +
                        "  \"password\": \"x\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(userService)
                .authenticate(
                        eq("tester@example.com"),
                        eq("x"));
    }
}