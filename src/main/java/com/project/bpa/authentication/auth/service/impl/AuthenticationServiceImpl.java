package com.project.bpa.authentication.auth.service.impl;

import com.project.bpa.authentication.auth.dto.request.LoginRequest;
import com.project.bpa.authentication.auth.dto.request.RefreshTokenRequest;
import com.project.bpa.authentication.auth.dto.request.RegisterRequest;
import com.project.bpa.authentication.auth.dto.response.AuthenticationResponse;
import com.project.bpa.authentication.auth.dto.response.RefreshTokenResponse;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.role.repository.RoleRepository;
import com.project.bpa.authentication.user.repository.UserRepository;
import com.project.bpa.authentication.auth.service.AuthenticationService;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.BadRequestException;
import com.project.bpa.exception.UnauthorizedException;
import com.project.bpa.security.user.CustomUserDetailsService;
import com.project.bpa.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailService;

    @Override
    @Transactional
    public ApiResponse<AuthenticationResponse> register(RegisterRequest body) {
        final boolean isUsernameExist = userRepository.existsByUsername(body.getUsername());
        if (isUsernameExist) { throw new BadRequestException("Username already exist!"); }

        final String encodedPassword = passwordEncoder.encode(body.getPassword());

        // Get the default role (e.g., "USER")
        String roleName = "USER";
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BadRequestException("Invalid role specified: " + roleName));


        // Create user builder with common fields
        User user = User.builder()
                .username(body.getUsername())
                .password(encodedPassword)
                .email(body.getEmail())
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .phoneNumber(body.getPhone())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        final UserDetails userDetails = userDetailService.loadUserByUsername(body.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.successCreated(response);
    }

    @Override
    @Transactional
    public ApiResponse<AuthenticationResponse> login(LoginRequest body) {
        User user = userRepository.findByUsername(body.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid credential!"));

        final boolean isPasswordMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new UnauthorizedException("Invalid credential!");
        }

        final String token = jwtUtil.generateToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.success(response);
    }

    @Override
    @Transactional
    public ApiResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest body) {
        // Extract username from the refresh token
        String username = jwtUtil.extractUsername(body.getRefreshToken());

        // Load user details
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // Validate the refresh token
        if (!jwtUtil.validateToken(body.getRefreshToken(), userDetails)) {
            throw new UnauthorizedException("Invalid refresh token!");
        }

        // Generate new tokens
        String newAccessToken = jwtUtil.generateToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Build the response
        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        return ApiResponse.success(response);
    }
}
