package file.univ_playground.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void creation(){
        User user1 = User.builder()
                .email("parksj914@naver.com")
                .nickName("WhiteOwl")
                .password("RJScnr1533")
                .name("박성주")
                .age(27)
                .level(100L)
                .build();

        assertAll(
                () -> assertThat(user1.getEmail())
                        .isEqualTo("parksj914@naver.com"),
                () -> assertThat(user1.getNickName())
                        .isEqualTo("WhiteOwl"),
                () -> assertThat(user1.getPassword())
                        .isEqualTo("RJScnr1533"),
                () -> assertThat(user1.getName())
                        .isEqualTo("박성주"),
                () -> assertThat(user1.getAge())
                        .isEqualTo(27),
                () -> assertThat(user1.isAdmin())
                        .isEqualTo(true),
                () -> assertThat(user1.isActive())
                        .isEqualTo(true)
        );

        user1.deactive();

        assertThat(user1.isActive()).isEqualTo(false);
    }
}