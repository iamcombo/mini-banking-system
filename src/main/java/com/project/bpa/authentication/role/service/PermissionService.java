package com.project.bpa.authentication.role.service;

import com.project.bpa.authentication.role.dto.request.CreatePermissionRequest;
import com.project.bpa.authentication.role.dto.request.CreateRolePermissionRequest;
import com.project.bpa.authentication.role.entity.Permission;
import com.project.bpa.exception.ApiResponse;

import java.util.List;

public interface PermissionService {
    ApiResponse<Permission> createPermission(CreatePermissionRequest body);
    ApiResponse<List<Permission>> getAllPermissions();
    ApiResponse<Void> assignPermissionToRole(CreateRolePermissionRequest body);
}
