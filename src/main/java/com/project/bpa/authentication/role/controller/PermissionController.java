package com.project.bpa.authentication.role.controller;

import com.project.bpa.authentication.role.dto.request.CreatePermissionRequest;
import com.project.bpa.authentication.role.dto.request.CreateRolePermissionRequest;
import com.project.bpa.authentication.role.entity.Permission;
import com.project.bpa.authentication.role.service.PermissionService;
import com.project.bpa.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission", description = "Permission endpoints")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create permission", description = "Create a new permission")
    public ApiResponse<Permission> createPermission(@RequestBody @Valid CreatePermissionRequest body) {
        return permissionService.createPermission(body);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all permissions", description = "Get all permissions")
    public ApiResponse<List<Permission>> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Assign permission to role", description = "Assign permission to role")
    public ApiResponse<Void> assignPermissionToRole(@RequestBody @Valid CreateRolePermissionRequest body) {
        return permissionService.assignPermissionToRole(body);
    }
}
