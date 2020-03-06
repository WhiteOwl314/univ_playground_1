package file.univ_playground.service;

import file.univ_playground.domain.User;
import file.univ_playground.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    //Service Constructor
    //+ Create UserRepository instance
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User addUser(
            String email,
            String password,
            String nickName,
            String name,
            Integer age
    ) {

        User user = User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .age(age)
                .build();

        return userRepository.save(user);
    }
}
