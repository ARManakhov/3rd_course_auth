package com.rat.squad.auth.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rat.squad.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Component for jwt Bearer token generation, and validation
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey; //key, that used for jwt token encoding
    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds; //key's life time
    private Algorithm algorithm;

    /**
     * method for initialization algorithm
     */
    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * method for creating jwt token based on user info
     * @param user user info
     * @return Bearer jwt token
     */
    public String createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());
        return JWT.create()
                .withClaim("roles", roles)
                .withClaim("id", user.getId())
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    /**
     * method for token validation
     * @param token Bearer jwt token
     * @return true on success
     */
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (IllegalArgumentException | JWTVerificationException exception) {
            throw new JWTVerificationException("Expired or invalid JWT token");
        }
    }
}
