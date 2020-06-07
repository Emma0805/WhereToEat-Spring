package com.chenyuegu.WhereToEat.Auth;

import com.chenyuegu.WhereToEat.Auth.DTO.Token;
import com.chenyuegu.WhereToEat.User.DTO.User;
import org.springframework.web.bind.annotation.*;

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
