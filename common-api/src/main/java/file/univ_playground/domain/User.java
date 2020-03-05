package file.univ_playground.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private Integer age;

    private String hobby;

    private String birthday; //TODO: 생일 구현

    @Column(name = "Community_id")
    private Long communityId;
}
