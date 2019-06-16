package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.Place;
import com.chenyuegu.WhereToEat.User.DTO.User;
import org.bson.types.ObjectId;
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

    UserService(UserRepository userRepository, MongoTemplate mongoTemplate){
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(new ObjectId(id));
    }

    public User registerNewUser(User user){
        return userRepository.save(user);
    }

    public User login(User user) {
        User res = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(res == null) return null;
        res.setPassword(null);
        return res;
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
