package com.chenyuegu.WhereToEat.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}
