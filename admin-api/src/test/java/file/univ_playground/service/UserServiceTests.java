package file.univ_playground.service;

import file.univ_playground.domain.User;
import file.univ_playground.exception.EmailExistedException;
import file.univ_playground.exception.EmailNotExistedException;
import file.univ_playground.exception.PasswordWrongException;
import file.univ_playground.exception.UserNotFoundException;
import file.univ_playground.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(
                userRepository, passwordEncoder);
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
    public void getUserWithExisted(){
        Long userId = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;

        User mockUser = User.builder()
                .id(userId)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();

        given(userRepository.findById(eq(1004L))).willReturn(Optional.ofNullable(mockUser));

        User user = userService.getUser(userId);

        assertAll(
                () -> assertThat(user.getId()).isEqualTo(userId),
                () -> assertThat(user.getEmail()).isEqualTo(email),
                () -> assertThat(user.getNickName()).isEqualTo(nickName),
                () -> assertThat(user.getPassword()).isEqualTo(password),
                () -> assertThat(user.getAge()).isEqualTo(age) //TODO: 이름 한글입력 안되는 것 수정
        );
    }

    @Test
    public void getUserWithNotExisted(){
        Long userId = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;

        given(userRepository.findById(1004L)).willReturn(Optional.empty());

        assertThatThrownBy(
                () -> {
                    userService.getUser(userId);
                }).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void addUser(){
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;
        Long level = 50L;

        User mockUser = User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .level(level)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService
                .addUser(email, password, nickName, name, age, level);

        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(email),
                () -> assertThat(user.getNickName()).isEqualTo(nickName),
                () -> assertThat(user.getPassword()).isEqualTo(password),
                () -> assertThat(user.getAge()).isEqualTo(age) //TODO: 이름 한글입력 안되는 것 수정
        );
    }

    @Test
    public void addUserWithExistedEmail(){
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;
        Long level = 50L;

        User user = User.builder().build();
        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));


        assertThatThrownBy(
                () -> {
                    userService.addUser(
                            email,
                            password,
                            nickName,
                            name,
                            age,
                            level
                    );
                }).isInstanceOf(EmailExistedException.class);
    }

    //인증 성공
    @Test
    public void authenticateWithValidAttributes(){
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;
        Long level = 50L;

        User mockUser = User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .level(level)
                .build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any()))
                .willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail()).isEqualTo(email);

    }

    //로그인 시 이메일이 없을때
    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());


        assertThatThrownBy(
                () -> {
                    userService.authenticate(email, password);
                }).isInstanceOf(EmailNotExistedException.class);
    }

    // 비밀번호가 틀릴때
    @Test
    public void authenticateWithWrongPassword(){
        Long id = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;


        User mockUser = User.builder()
                .id(1004L)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();
        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any()))
                .willReturn(false);

        assertThrows(
                PasswordWrongException.class,
                () -> userService
                        .authenticate(email, password)
                );
    }

    @Test
    public void updatePassword(){
        Long id = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;
        String updatePassword = "RJScnr0329";

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();
        given(userRepository.findById(id)).willReturn(Optional.ofNullable(mockUser));

        User user = userService.updatePassword(id, updatePassword);

        assertAll(
                () -> assertThat(user.getId()).isEqualTo(id),
                () -> assertThat(user.getPassword()).isEqualTo(updatePassword)
        );

    }

    @Test
    public void updateInformation(){
        Long id = 1004L;
        String email = "parksj914@naver.com";
        String password = "RJScnr1533";
        String nickName = "WhiteOwl";
        String name = "박성주";
        Integer age = 27;

        String updateNickName = "WhiteMonkey";
        String updateName = "성주";
        String updateJob = "development";
        String updatePhoneNumber = "010-3925-1533";
        String updateHobby = "Reading book";

        Optional<User> mockUser = Optional.ofNullable(User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build());

        given(userRepository.findById(id)).willReturn(mockUser);

        User user = userService.updateInformation(
                id,
                updateNickName,
                updateName,
                updateJob,
                updatePhoneNumber,
                updateHobby
        );



        assertAll(
                () -> assertThat(user.getId()).isEqualTo(id),
                () -> assertThat(user.getEmail()).isEqualTo(email),
                () -> assertThat(user.getPassword()).isEqualTo(password),
                () -> assertThat(user.getNickName()).isEqualTo(updateNickName),
                () -> assertThat(user.getName()).isEqualTo(updateName),
                () -> assertThat(user.getAge()).isEqualTo(age),
                () -> assertThat(user.getJob()).isEqualTo(updateJob),
                () -> assertThat(user.getPhoneNumber()).isEqualTo(updatePhoneNumber),
                () -> assertThat(user.getHobby()).isEqualTo(updateHobby)
        );

    }

    @Test
    public void deactiveUser(){
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

        given(userRepository.findById(id)).willReturn(Optional.ofNullable(mockUser));

        User user = userService.deactiveUser(id);

        verify(userRepository).findById(id);

        assertAll(
                () -> assertThat(user.isActive()).isFalse(),
                () -> assertThat(user.isAdmin()).isFalse()
        );


    }

}