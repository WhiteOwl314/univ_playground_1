package file.univ_playground.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//TODO: delete 요청을 위해 delete 값 defalte 가 false가 되도록
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotNull
    private Integer age; //TODO: 생일값으로 나이 구하기

    private String jop;

    private String phoneNumber;

    private String hobby;

    @Embedded
    @Valid
    private Birthday birthday;

    @Column(name = "Community_id")
    private Long communityId;
}
