package com.chenyuegu.WhereToEat.User.Auth;

import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.Auth.DTO.Token;
import com.chenyuegu.WhereToEat.User.DTO.User;
import com.chenyuegu.WhereToEat.User.UserRepository;
import com.chenyuegu.WhereToEat.User.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return new Token(jwtTokenService.generateAuthToken(), jwtTokenService.generateRefreshToken());
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
        return login(user);
    }

    public Token refreshToken(Token token) throws Exception {
        if(jwtTokenService.varifyAuthTokenIgnoreExpiredTime(token.getAuthToken()) && jwtTokenService.varifyRefreshToken(token.getRefreshToken())){
            token.setAuthToken(jwtTokenService.generateAuthToken());
        }else{
            throw new Exception("Unauthorized");
        }
        return token;
    }
}
