package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.Auth.PasswordService;
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
    UserService(UserRepository userRepository, MongoTemplate mongoTemplate, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.passwordService = passwordService;
    }

    public User getUserById(String id) {
        User user = null;
        Optional<User> userRes = userRepository.findById(new ObjectId(id));
        if(userRes.isPresent()){
            user = userRes.get();
            user.setPassword(null);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<Place> updatePlaces(String userId, List<Place> places) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update();
        update.set("places", places);
        mongoTemplate.updateFirst(query, update, User.class);
        return places;
    }

}
