package com.project.bpa.authentication.user.dto.request;

import com.project.bpa.authentication.auth.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private UserStatusEnum status;
    private String firstname;
    private String lastname;
}
