package com.project.bpa.authentication.role.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRolePermissionRequest {

    private Long roleId;
    private Long permissionId;
}
