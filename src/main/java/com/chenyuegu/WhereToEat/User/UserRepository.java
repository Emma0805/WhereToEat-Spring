package com.chenyuegu.WhereToEat.User;

import com.chenyuegu.WhereToEat.User.DTO.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByEmailAndPassword(String email, String password);
}
