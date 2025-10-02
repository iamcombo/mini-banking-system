package com.project.bpa.authentication.user.service.impl;

import com.project.bpa.authentication.auth.enums.UserStatusEnum;
import com.project.bpa.authentication.user.dto.request.UpdateUserRequest;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.repository.UserRepository;
import com.project.bpa.authentication.user.service.UserService;
import com.project.bpa.common.specification.SpecificationBuilder;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<User>> listUsers(String search, UserStatusEnum status, Pageable pageable) {
        
        // Build the specification using our SpecificationBuilder
        Specification<User> spec = SpecificationBuilder.<User>builder()
                .like("username", search)
                .equal("status", status)
                .build();

        // Execute the query with the built specification
        Page<User> users = userRepository.findAll(spec, pageable);

        return ApiResponse.success(users.getContent());
    }

    @Override
    public ApiResponse<User> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        return ApiResponse.success(user);
    }

    @Override
    @Transactional
    public ApiResponse<String> updateUser(Long id, UpdateUserRequest body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        if (body.getStatus() != null) {
            user.setStatus(body.getStatus());
        }

        if (body.getFirstname() != null && !body.getFirstname().trim().isEmpty()) {
            user.setFirstName(body.getFirstname().trim());
        }

        if (body.getLastname() != null && !body.getLastname().trim().isEmpty()) {
            user.setLastName(body.getLastname().trim());
        }

        userRepository.save(user);
        return ApiResponse.success("User updated successfully");
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        userRepository.deleteById(id);
        return ApiResponse.success("User deleted successfully!");
    }
}
