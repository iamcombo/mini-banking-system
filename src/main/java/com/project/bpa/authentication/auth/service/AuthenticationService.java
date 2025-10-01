package com.project.bpa.authentication.auth.service;

import com.project.bpa.authentication.auth.dto.request.LoginRequest;
import com.project.bpa.authentication.auth.dto.request.RefreshTokenRequest;
import com.project.bpa.authentication.auth.dto.request.RegisterRequest;
import com.project.bpa.authentication.auth.dto.response.AuthenticationResponse;
import com.project.bpa.authentication.auth.dto.response.RefreshTokenResponse;
import com.project.bpa.exception.ApiResponse;

public interface AuthenticationService {
    ApiResponse<AuthenticationResponse> register(RegisterRequest body);
    ApiResponse<AuthenticationResponse> login(LoginRequest body);
    ApiResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest body);
}
