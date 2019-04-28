package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUserById(long id){
        return new User(123l,"firstName", "lastName");
    }
}
