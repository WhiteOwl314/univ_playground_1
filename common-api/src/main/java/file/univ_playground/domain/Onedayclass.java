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
@Builder
public class Onedayclass {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private User onedayclassManager;

    private List<User> onedayclassSubManager;

    private Community community;

    private List<User> member;


}
