package com.chenyuegu.WhereToEat.User.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chenyuegu.WhereToEat.User.Auth.DTO.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenService implements Serializable {

    @Value("${jwt.secret.auth.key}")
    private String authSecret;

    @Value("${jwt.secret.refresh.key}")
    private String refreshSecret;


    public String generateAuthToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(authSecret);
            String token = JWT.create()
                    .withIssuer("whereToEatSpring")
                    .withAudience("whereToEatAngular")
                    .withIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                    .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.UTC)))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
        }
        return null;
    }

    public String generateRefreshToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            String token = JWT.create()
                    .withIssuer("whereToEatSpring")
                    .withAudience("whereToEatAngular")
                    .withIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                    .withExpiresAt(Date.from(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.UTC)))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("RefreshToken: Invalid Signing configuration / Couldn't convert Claims.");
        }
        return null;
    }

    public boolean varifyAuthToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(authSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("whereToEatSpring")
                    .acceptExpiresAt(60 * 60 * 8)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
            return false;
        }
    }

    public boolean varifyRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("whereToEatSpring")
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
            return false;
        }
    }
}