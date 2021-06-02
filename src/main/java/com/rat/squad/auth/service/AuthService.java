package com.rat.squad.auth.service;

import com.rat.squad.auth.dto.ControllerResult;
import com.rat.squad.auth.dto.SignInRequest;
import com.rat.squad.auth.dto.SignInResponse;
import com.rat.squad.auth.dto.SignUpRequest;
import org.springframework.stereotype.Service;

/**
 * interface of auth service
 */
@Service
public interface AuthService {
    SignInResponse signUp(SignUpRequest form);

    SignInResponse signIn(SignInRequest form);

    ControllerResult validateToken(String token);
}
