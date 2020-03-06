package file.univ_playground.service;

import file.univ_playground.domain.User;
import file.univ_playground.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers(){
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(
                User.builder()
                        .email("parksj914@naver.com")
                        .nickName("WhiteOwl")
                        .password("RJScnr1533")
                        .name("박성주")
                        .age(27)
                        .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        User user1 = users.get(0);

        assertAll(
                () -> assertThat(user1.getNickName())
                        .isEqualTo("WhiteOwl"),
                () -> assertThat(user1.getEmail())
                        .isEqualTo("parksj914@naver.com"),
                () -> assertThat(user1.getPassword())
                        .isEqualTo("RJScnr1533"),
                () -> assertThat(user1.getAge())
                        .isEqualTo(27)
        );
    }

    @Test
    public void addUser(){
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;

        User mockUser = User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService
                .addUser(email, password, nickName, name, age);

        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(email),
                () -> assertThat(user.getNickName()).isEqualTo(nickName),
                () -> assertThat(user.getPassword()).isEqualTo(password),
                () -> assertThat(user.getAge()).isEqualTo(age) //TODO: 이름 한글입력 안되는 것 수정
        );

    }

}