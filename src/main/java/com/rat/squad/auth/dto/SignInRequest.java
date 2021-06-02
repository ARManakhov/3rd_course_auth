package com.rat.squad.auth.dto;

import lombok.Data;

/**
 * Data class, that represents sign in request body
 */
@Data
public class SignInRequest {
    private String username;
    private String password;
}