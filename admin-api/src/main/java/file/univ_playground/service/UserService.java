package file.univ_playground.service;

import file.univ_playground.domain.User;
import file.univ_playground.exception.EmailExistedException;
import file.univ_playground.exception.EmailNotExistedException;
import file.univ_playground.exception.PasswordWrongException;
import file.univ_playground.exception.UserNotFoundException;
import file.univ_playground.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //Service Constructor
    //+ Create UserRepository instance
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    //회원가입
    public User addUser(
            String email,
            String password,
            String nickName,
            String name,
            Integer age,
            Long level) {

        //중복된 email이 있는지 조회
        Optional<User> existed = userRepository.findByEmail(email);

        //중복된 email이 있으면 에러 던지기
        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }

        //spring security 소속이며 패스워드를 암호화 해준다
        String encodedPassword = passwordEncoder.encode(password);

        //encoded된 패스워드로 유저아이디를 만든다
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .nickName(nickName)
                .name(name)
                .age(age)
                .level(level)
                .build();

        //만든 유저아이디를 저장소에 저장
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

    //아이디, 비밀번호 인증
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EmailNotExistedException(email)
                );

        //입력한 패스워드와 다를때
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException();
        }

        return user;
    }
}
