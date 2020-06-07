package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.DTO.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add/place/{userId}")
    public List<Place> updatePlaces(@PathVariable String userId, @RequestBody List<Place> places) {
        return userService.updatePlaces(userId, places);
    }

}
