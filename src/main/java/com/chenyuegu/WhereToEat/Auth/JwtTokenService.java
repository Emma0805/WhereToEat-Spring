package com.chenyuegu.WhereToEat.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenService implements Serializable {

    @Value("${jwt.secret.auth.key}")
    private String authSecret;

    @Value("${jwt.secret.refresh.key}")
    private String refreshSecret;


    public String generateAuthToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(authSecret);
            String token = JWT.create()
                    .withIssuer("whereToEatSpring")
                    .withAudience("whereToEatAngular")
                    .withSubject(userId)
                    .withIssuedAt(Date.from(Instant.now()))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(10 * 60)))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
        }
        return null;
    }

    public String generateRefreshToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            String token = JWT.create()
                    .withIssuer("whereToEatSpring")
                    .withAudience("whereToEatAngular")
                    .withSubject(userId)
                    .withIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                    .withExpiresAt(Date.from(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.UTC)))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("RefreshToken: Invalid Signing configuration / Couldn't convert Claims.");
        }
        return null;
    }

    public String varifyAuthTokenIgnoreExpiredTime(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(authSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("whereToEatSpring")
                    .acceptExpiresAt(60 * 60 * 8)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
            return null;
        }
    }

    public String varifyRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("whereToEatSpring")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTCreationException exception) {
            log.error("AuthToken: Invalid Signing configuration / Couldn't convert Claims.");
            return null;
        }
    }
}