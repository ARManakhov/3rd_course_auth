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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements SignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Override
    public SignInResponse signIn(SignInRequest form) {
        User user = User.builder()
                .username(form.getUsername())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .build();
        return signIn(user);
    }

    private SignInResponse signIn(User user){
        String token = jwtTokenProvider.createToken(userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("username with username: " + user.getUsername() + " not found")));
        return SignInResponse.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }

    @Override
    public ControllerResult validateToken(String token) {
        return ControllerResult.successResult(String.valueOf(jwtTokenProvider.validateToken(token)));
    }
}
