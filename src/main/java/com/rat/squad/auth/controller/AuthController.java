package com.rat.squad.auth.controller;

import com.rat.squad.auth.dto.ControllerResult;
import com.rat.squad.auth.dto.SignInRequest;
import com.rat.squad.auth.dto.SignInResponse;
import com.rat.squad.auth.dto.SignUpRequest;
import com.rat.squad.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService signUpService;

    @PostMapping("/sign_in")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        return signUpService.signIn(request);
    }

    @PostMapping("/sign_up")
    public SignInResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }

    @PostMapping("/valid")
    public ControllerResult signUp(@RequestHeader("Authorization") String token) {
        return signUpService.validateToken(token);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsernameNotFoundException.class})
    public ControllerResult handleNotFoundError(Exception e) {
        return ControllerResult.failResult(e.getMessage());

    }
}
