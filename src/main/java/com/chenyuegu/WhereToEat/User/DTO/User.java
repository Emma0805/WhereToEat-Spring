package com.chenyuegu.WhereToEat.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String lastName;
}
