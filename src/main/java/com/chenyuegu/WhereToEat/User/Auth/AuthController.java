package com.chenyuegu.WhereToEat.User.Auth;

import com.chenyuegu.WhereToEat.User.Auth.DTO.Token;
import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.DTO.User;
import com.chenyuegu.WhereToEat.User.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthController {
    private AuthService authService;

    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody User user) throws Exception {
        return authService.login(user);
    }

    @PostMapping("/register")
    public Token registerNewUser(@RequestBody User user) throws Exception {
        return authService.registerNewUser(user);
    }

    @PostMapping("/refresh/token")
    public Token refreshToken(@RequestBody Token token) throws Exception {
        return authService.refreshToken(token);
    }
}
