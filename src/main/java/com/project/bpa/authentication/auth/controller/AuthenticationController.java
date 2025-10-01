package com.project.bpa.authentication.auth.controller;

import com.project.bpa.authentication.auth.dto.request.LoginRequest;
import com.project.bpa.authentication.auth.dto.request.RefreshTokenRequest;
import com.project.bpa.authentication.auth.dto.request.RegisterRequest;
import com.project.bpa.authentication.auth.dto.response.AuthenticationResponse;
import com.project.bpa.authentication.auth.dto.response.RefreshTokenResponse;
import com.project.bpa.authentication.auth.service.AuthenticationService;
import com.project.bpa.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "")
    public ApiResponse<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest body) {
        return authenticationService.register(body);
    }

    @PostMapping("/login")
    @Operation(summary = "Login existing user", description = "")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid LoginRequest body) {
        return authenticationService.login(body);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Refresh access token")
    public ApiResponse<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest body) {
        return authenticationService.refreshToken(body);
    }
}
