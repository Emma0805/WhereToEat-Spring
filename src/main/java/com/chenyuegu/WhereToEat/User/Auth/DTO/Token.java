package com.chenyuegu.WhereToEat.User.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String authToken;
    private String refreshToken;
}
