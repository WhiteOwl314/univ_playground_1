package file.univ_playground.service;

import file.univ_playground.domain.User;
import file.univ_playground.exception.UserNotFoundException;
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

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
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

    public User updatePassword(Long id, String password) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setPassword(password);

        return user;
    }

    public User updateInformation(
            Long id,
            String nickName,
            String name,
            String job,
            String phoneNumber,
            String hobby
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setNickName(nickName);
        user.setName(name);
        user.setJob(job);
        user.setPhoneNumber(phoneNumber);
        user.setHobby(hobby);

        return user;
    }

    public User deactiveUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.deactive();

        return user;
    }
}
