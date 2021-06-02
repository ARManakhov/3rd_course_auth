package com.rat.squad.auth.service;

import com.rat.squad.auth.dto.ControllerResult;
import com.rat.squad.auth.dto.SignInRequest;
import com.rat.squad.auth.dto.SignInResponse;
import com.rat.squad.auth.dto.SignUpRequest;
import com.rat.squad.auth.entity.Role;
import com.rat.squad.auth.entity.User;
import com.rat.squad.auth.repository.UserRepository;
import com.rat.squad.auth.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of auth service interface
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * register new user and save to database
     * if user with that username already exists, throw exception DataIntegrityViolationException
     *
     * @param form request body from controller with new user data
     * @return data with token and username
     */
    @Override
    public SignInResponse signUp(SignUpRequest form) {
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return signIn(user);
    }


    /**
     * signing in user
     *
     * @param form request body from controller with user auth data
     * @return data with token and username
     */
    @Override
    public SignInResponse signIn(SignInRequest form) {
        User user = User.builder()
                .username(form.getUsername())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .build();
        return signIn(user);
    }


    /**
     * signing in user
     * check user existance in database, if found generate new token with jwtTokenProvider
     * if not found throws UsernameNotFoundException
     * @param user user data, with encrypted(hashed) password
     * @return data with token and username
     */
    private SignInResponse signIn(User user) {
        String token = jwtTokenProvider.createToken(userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("username with username: " + user.getUsername() + " not found")));
        return SignInResponse.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }

    /**
     * validate user by Bearer token, on failure throws JWTVerificationException exception
     * @param token user's Bearer token
     * @return ControllerResult with successes true
     */
    @Override
    public ControllerResult validateToken(String token) {
        return ControllerResult.successResult(String.valueOf(jwtTokenProvider.validateToken(token)));
    }
}
