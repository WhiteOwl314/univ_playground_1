package file.univ_playground.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    private String password;

    private String name;

    private Integer age;

    @Column(name = "Community_id")
    private Long communityId;
}
