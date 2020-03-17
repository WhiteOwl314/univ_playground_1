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

    @GetMapping("/users/{id}")
    public User getUser(
            @PathVariable("id") Long userId
    ){
        User user = userService.getUser(userId);
        return user;
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
        Long level = resource.getLevel();

        User user = userService.addUser(
                email,
                password,
                nickName,
                name,
                age,
                level
        );

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{id}/password")
    public String updatePasssword(
            @PathVariable("id") Long id,
            @RequestBody User resource
    ){
        String password = resource.getPassword();
        userService.updatePassword(id, password);

        return "{}";
    }

    @PatchMapping("/users/{id}/information")
    public String updateInformation(
            @PathVariable("id") Long id,
            @RequestBody User resource
    ){
        String nickName = resource.getNickName();
        String name = resource.getName();
        String job = resource.getJob();
        String phoneNumber = resource.getPhoneNumber();
        String hobby = resource.getHobby();

        userService.updateInformation(
                id,
                nickName,
                name,
                job,
                phoneNumber,
                hobby
        );

        return "{}";
    }

    @DeleteMapping("users/{id}")
    public String deactivate(@PathVariable("id") Long id){
        userService.deactiveUser(id);

        return "{}";
    }
}
