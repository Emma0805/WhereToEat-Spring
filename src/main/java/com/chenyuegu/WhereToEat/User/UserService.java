package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(new ObjectId(id));
    }

    public User registerNewUser(User user){
        return userRepository.save(user);
    }
}
