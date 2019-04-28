package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }
}
