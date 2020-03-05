package file.univ_playground.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Embeddable
public class Birthday {

    private Integer yearOfBirthday;
    private Integer monthOfBirthday;
    private Integer dayOfBirthday;

    private Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        //1-12까지 반환
        this.monthOfBirthday = birthday.getMonthValue();
        //입력된 달에 따라서 일을 반환
        this.dayOfBirthday = birthday.getDayOfMonth();
    }

    //static 으로 쉽게 Birthday 객체 생성
    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }
}
