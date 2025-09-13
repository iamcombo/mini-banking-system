package com.project.bpa.authentication.auth.controller;

import com.project.bpa.authentication.auth.dto.request.LoginRequest;
import com.project.bpa.authentication.auth.dto.request.RegisterRequest;
import com.project.bpa.authentication.auth.dto.response.AuthenticationResponse;
import com.project.bpa.authentication.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest body) {
        return authenticationService.register(body);
    }

    @PostMapping("/login")
    public  AuthenticationResponse login(@RequestBody @Valid LoginRequest body) {
        return authenticationService.login(body);
    }
}
