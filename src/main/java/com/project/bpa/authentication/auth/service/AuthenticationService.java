package com.project.bpa.authentication.auth.service;

import com.project.bpa.authentication.auth.dto.request.LoginRequest;
import com.project.bpa.authentication.auth.dto.request.RegisterRequest;
import com.project.bpa.authentication.auth.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest body);
    AuthenticationResponse login(LoginRequest body);
}
