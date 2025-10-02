package com.project.bpa.authentication.user.service;

import com.project.bpa.authentication.auth.enums.UserStatusEnum;
import com.project.bpa.authentication.user.dto.request.UpdateUserRequest;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.exception.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    ApiResponse<List<User>> listUsers(String search, UserStatusEnum status, Pageable pageable);
    ApiResponse<User> getUserById(Long id);
    ApiResponse<String> updateUser(Long id, UpdateUserRequest body);
    ApiResponse<String> deleteUser(Long id);
}
