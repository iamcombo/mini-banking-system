package com.project.bpa.auth.service.impl;

import com.project.bpa.auth.dto.request.LoginRequest;
import com.project.bpa.auth.dto.request.RegisterRequest;
import com.project.bpa.auth.dto.response.AuthenticationResponse;
import com.project.bpa.auth.entity.Role;
import com.project.bpa.auth.entity.User;
import com.project.bpa.auth.repository.RoleRepository;
import com.project.bpa.auth.repository.UserRepository;
import com.project.bpa.auth.service.AuthenticationService;
import com.project.bpa.exception.BadRequestException;
import com.project.bpa.exception.UnauthorizedException;
import com.project.bpa.user.service.UserService;
import com.project.bpa.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userDetailService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(RegisterRequest body) {
        final boolean isUsernameExist = userRepository.existsByUsername(body.getUsername());
        if (isUsernameExist) { throw new BadRequestException("Username already exist!"); }

        final String encodedPassword = passwordEncoder.encode(body.getPassword());

        // Get the default role (e.g., "USER") if none is provided
        String roleName = (body.getRoleName() != null && !body.getRoleName().isEmpty()) ?
                body.getRoleName() : "USER";

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BadRequestException("Invalid role specified: " + roleName));


        // Create user builder with common fields
        User user = User.builder()
                .username(body.getUsername())
                .password(encodedPassword)
                .email(body.getEmail())
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .role(role)
                .build();

        userRepository.save(user);

        final UserDetails userDetails = userDetailService.loadUserByUsername(body.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(token)
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest body) {
        User user = userRepository.findByUsername(body.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid credential!"));

        final boolean isPasswordMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new UnauthorizedException("Invalid credential!");
        }

        String token = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }
}
