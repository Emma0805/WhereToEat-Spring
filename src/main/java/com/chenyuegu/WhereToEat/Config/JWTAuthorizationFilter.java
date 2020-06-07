package com.chenyuegu.WhereToEat.Config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    String authSecret;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String authSecret) {
        super(authManager);
        this.authSecret = authSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader("Authorization");
        if(token == null) {
            chain.doFilter(req, res);
            return;
        }

        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("whereToEatSpring")
                .build();
        verifier.verify(token);


        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, new ArrayList<>()));
        chain.doFilter(req, res);
    }
}