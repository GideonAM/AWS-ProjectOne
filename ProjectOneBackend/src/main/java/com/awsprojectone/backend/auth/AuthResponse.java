package com.awsprojectone.backend.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String userEmail;
    private String userId;
    private String jwt;
    private boolean isAdmin;
}
