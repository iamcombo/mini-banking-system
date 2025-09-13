package com.project.bpa.example;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.annotation.RequiresPermission;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/public")
    public ApiResponse<String> publicEndpoint() {
        return ApiResponse.success("This is a public endpoint, Public data retrieved successfully");
    }

    @GetMapping("/user")
    @RequiresPermission("user:read")
    public ApiResponse<String> userEndpoint() {
        return ApiResponse.success("This is a user endpoint, User data retrieved successfully");
    }

    @GetMapping("/admin")
    @RequiresPermission("admin:access")
    public ApiResponse<String> adminEndpoint() {
        return ApiResponse.success("This is an admin endpoint, Admin data retrieved successfully");
    }

    @GetMapping("/user/read")
    @RequiresPermission("user:read")
    public ApiResponse<String> userReadEndpoint() {
        return ApiResponse.success("User read operation successful");
    }

    @PostMapping("/user")
    @RequiresPermission("user:create")
    public ApiResponse<String> createUser() {
        return ApiResponse.success("User created successfully");
    }

    @PutMapping("/user/{id}")
    @RequiresPermission("user:update")
    public ApiResponse<String> updateUser(@PathVariable Long id) {
        return ApiResponse.success("User " + id + " updated successfully");
    }

    @DeleteMapping("/user/{id}")
    @RequiresPermission("user:delete")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        return ApiResponse.success("User " + id + " deleted successfully");
    }
}
