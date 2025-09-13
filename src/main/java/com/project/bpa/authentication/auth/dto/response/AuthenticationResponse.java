package com.project.bpa.authentication.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bpa.authentication.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    // Token Information
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    // Token Metadata
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime issuedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime accessTokenExpiresAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime refreshTokenExpiresAt;
    private Long accessTokenExpiresIn; // seconds
    private Long refreshTokenExpiresIn; // seconds

    // User Information
    private User user;

    // Application Information
    private String applicationName;
    private String version;
    private String environment;

    // Security Information
    private List<String> scopes;
    private List<String> roles;
    private List<String> permissions;

    // Status Information
    private String status;
    private String message;
}
