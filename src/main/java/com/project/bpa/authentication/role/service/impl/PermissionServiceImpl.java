package com.project.bpa.authentication.role.service.impl;

import com.project.bpa.authentication.role.dto.request.CreatePermissionRequest;
import com.project.bpa.authentication.role.dto.request.CreateRolePermissionRequest;
import com.project.bpa.authentication.role.entity.Permission;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.role.repository.PermissionRepository;
import com.project.bpa.authentication.role.repository.RoleRepository;
import com.project.bpa.authentication.role.service.PermissionService;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public ApiResponse<Permission> createPermission(CreatePermissionRequest body) {
        if (permissionRepository.existsByName(body.getName())) {
            throw new IllegalArgumentException("Permission with name " + body.getName() + " already exists");
        }

        Permission permission = new Permission();
        permission.setName(body.getName());
        permission.setDescription(body.getDescription());
        permission.setCategory(body.getCategory());

        Permission savedPermission = permissionRepository.save(permission);
        return ApiResponse.success(savedPermission);
    }

    public ApiResponse<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return ApiResponse.success(permissions);
    }

    @Transactional
    public ApiResponse<Void> assignPermissionToRole(CreateRolePermissionRequest body) {
        Long roleId = body.getRoleId();
        Long permissionId = body.getPermissionId();

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found with id: " + permissionId));

        // Check if the permission is already assigned
        boolean alreadyAssigned = role.getPermissions().stream()
                .anyMatch(pm -> pm.getId().equals(permissionId));

        if (!alreadyAssigned) {
            role.addPermission(permission);
            roleRepository.save(role);
        }

        return ApiResponse.success();
    }
}
