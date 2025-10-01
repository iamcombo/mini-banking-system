package com.project.bpa.authentication.role.dto.request;

import com.project.bpa.authentication.enums.PermissionCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePermissionRequest {
    private String name;
    private String description;
    private PermissionCategoryEnum category;
}
