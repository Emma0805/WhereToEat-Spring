package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.DTO.User;
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
public class UserService {
    UserRepository userRepository;
    MongoTemplate mongoTemplate;
    PasswordService passwordService;

    @Autowired
    UserService(UserRepository userRepository, MongoTemplate mongoTemplate, PasswordService passwordService){
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.passwordService = passwordService;
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(new ObjectId(id));
    }

    public User registerNewUser(User user) throws Exception {
        User res = userRepository.findByEmail(user.getEmail());
        if(res != null){
            throw new Exception("Email already exist.");
        }
        String password = passwordService.generateStorngPasswordHash(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        user.setPassword(null);
        return user;
    }

    public User login(User user) throws Exception {
        User res = userRepository.findByEmail(user.getEmail());
        if(res == null) return null;
        if(passwordService.validatePassword(user.getPassword(), res.getPassword())){
            res.setPassword(null);
            return res;
        }
        return null;
    }

    public List<Place> updatePlaces(String userId, List<Place> places){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update();
        update.set("places", places);
        mongoTemplate.updateFirst(query, update, User.class);
        return places;
    }
}
