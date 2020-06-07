package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.DTO.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/add/place/{userId}")
    public List<Place> updatePlaces(@PathVariable String userId, @RequestBody List<Place> places) {
        return userService.updatePlaces(userId, places);
    }

}
