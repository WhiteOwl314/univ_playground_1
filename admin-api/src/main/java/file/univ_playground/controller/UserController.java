package file.univ_playground.controller;

import file.univ_playground.domain.User;
import file.univ_playground.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();
        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();
        String nickName = resource.getNickName();
        String name = resource.getName();
        Integer age = resource.getAge();

        User user = userService.addUser(
                email,
                password,
                nickName,
                name,
                age
        );

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
