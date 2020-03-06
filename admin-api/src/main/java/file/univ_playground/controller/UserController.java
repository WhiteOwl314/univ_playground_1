package file.univ_playground.controller;

import file.univ_playground.domain.User;
import file.univ_playground.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getusers();
        return users;
    }
}
