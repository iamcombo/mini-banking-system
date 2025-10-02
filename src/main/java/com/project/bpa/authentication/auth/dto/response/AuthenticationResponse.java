package com.project.bpa.authentication.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    // Token Information
    private String accessToken;
    private String refreshToken;
}
