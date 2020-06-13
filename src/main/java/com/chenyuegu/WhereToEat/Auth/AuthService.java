package com.chenyuegu.WhereToEat.Auth;

import com.chenyuegu.WhereToEat.Auth.DTO.Token;
import com.chenyuegu.WhereToEat.User.DTO.User;
import com.chenyuegu.WhereToEat.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    UserService userService;
    PasswordService passwordService;
    JwtTokenService jwtTokenService;

    @Autowired
    AuthService(UserService userService, PasswordService passwordService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.jwtTokenService = jwtTokenService;
    }


    public Token login(User user) throws Exception {
        User res = userService.getUserByEmail(user.getEmail());
        if (res == null) return null;
        if (passwordService.validatePassword(user.getPassword(), res.getPassword())) {
            return new Token(jwtTokenService.generateAuthToken(res.getId()), jwtTokenService.generateRefreshToken(res.getId()), res.getId());
        }
        return null;
    }

    public Token registerNewUser(User user) throws Exception {
        User res = userService.getUserByEmail(user.getEmail());
        if (res != null) {
            throw new Exception("Email already exist.");
        }
        String password = passwordService.generateStorngPasswordHash(user.getPassword());
        user.setPassword(password);
        userService.save(user);

        return new Token(jwtTokenService.generateAuthToken(res.getId()), jwtTokenService.generateRefreshToken(res.getId()), res.getId());
    }

    public Token refreshToken(Token token) throws Exception {
        if (jwtTokenService.varifyAuthTokenIgnoreExpiredTime(token.getAuthToken()) != null
                && jwtTokenService.varifyRefreshToken(token.getRefreshToken()) != null
                && jwtTokenService.varifyRefreshToken(token.getRefreshToken())
                .equals(jwtTokenService.varifyAuthTokenIgnoreExpiredTime(token.getAuthToken()))
        ) {
            String userId = jwtTokenService.varifyRefreshToken(token.getRefreshToken());
            token.setAuthToken(jwtTokenService.generateAuthToken(userId));
        } else {
            throw new Exception("Unauthorized");
        }
        return token;
    }
}
