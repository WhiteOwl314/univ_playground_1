package file.univ_playground.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "OndayClass")
public class Onedayclass {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private User ondayclassManager;

    private List<User> ondayclassSubManager;

    private Community community;

    private List<User> member;


}
