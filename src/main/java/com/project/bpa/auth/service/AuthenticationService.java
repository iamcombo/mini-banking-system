package com.project.bpa.auth.service;

import com.project.bpa.auth.dto.request.LoginRequest;
import com.project.bpa.auth.dto.request.RegisterRequest;
import com.project.bpa.auth.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest body);
    AuthenticationResponse login(LoginRequest body);
}
