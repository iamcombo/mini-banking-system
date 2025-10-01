package com.project.bpa.authentication.user.controller;

import com.project.bpa.authentication.auth.enums.UserStatusEnum;
import com.project.bpa.authentication.user.dto.request.UpdateUserRequest;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.service.UserService;
import com.project.bpa.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User endpoints")
public class UserController {

    private final UserService service;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List user", description = "List all users")
    ApiResponse<List<User>> listUsers(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) UserStatusEnum status,
        Pageable pageable
    ) {
        return this.service.listUsers(search, status, pageable);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user detail", description = "Get user detail by id")
    ApiResponse<User> getUserById(Long id) {
        return this.service.getUserById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update user", description = "Update user by id")
    ApiResponse<String> updateUser(Long id, @RequestBody @Valid UpdateUserRequest body) {
        return this.service.updateUser(id, body);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete user", description = "Delete user by id")
    ApiResponse<String> deleteUser(Long id) {
        return this.service.deleteUser(id);
    }
}
