//package com.project.bpa.authentication.auth.config;
//
//import com.project.bpa.authentication.role.entity.Permission;
//import com.project.bpa.authentication.role.entity.Role;
//import com.project.bpa.authentication.role.entity.RolePermission;
//import com.project.bpa.authentication.role.repository.PermissionRepository;
//import com.project.bpa.authentication.role.repository.RoleRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//@Profile("!test") // Don't run this in tests
//public class DataInitializer {
//
//    private final RoleRepository roleRepository;
//    private final PermissionRepository permissionRepository;
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        // Create default roles if they don't exist
//        Role adminRole = roleRepository.findByName("ADMIN")
//                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").description("Administrator with full access").build()));
//
//        Role userRole = roleRepository.findByName("USER")
//                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").description("Regular user with limited access").build()));
//
//        // Create default permissions if they don't exist
//        List<String> permissionNames = Arrays.asList(
//                "user:read", "user:write", "user:delete",
//                "account:read", "account:write", "account:delete",
//                "transaction:read", "transaction:write",
//                "report:generate", "audit:view"
//        );
//
//        for (String permissionName : permissionNames) {
//            if (!permissionRepository.existsByName(permissionName)) {
//                String category = permissionName.split(":")[0];
//                Permission permission = new Permission();
//                permission.setName(permissionName);
//                permission.setDescription("Allows " + permissionName.replace(":", " "));
//                permission.setCategory(category);
//                permissionRepository.save(permission);
//            }
//        }
//
//        // Assign all permissions to ADMIN role
//        if (adminRole.getRolePermissions().isEmpty()) {
//            permissionRepository.findAll().forEach(permission -> {
//                RolePermission rolePermission = new RolePermission();
//                rolePermission.setRole(adminRole);
//                rolePermission.setPermission(permission);
//                rolePermission.setActive(true);
//                adminRole.getRolePermissions().add(rolePermission);
//            });
//            roleRepository.save(adminRole);
//        }
//
//        // Assign basic permissions to USER role
//        if (userRole.getRolePermissions().isEmpty()) {
//            List<String> userPermissions = Arrays.asList("user:read", "account:read", "transaction:read");
//            permissionRepository.findByNameIn(userPermissions).forEach(permission -> {
//                RolePermission rolePermission = new RolePermission();
//                rolePermission.setRole(userRole);
//                rolePermission.setPermission(permission);
//                rolePermission.setActive(true);
//                userRole.getRolePermissions().add(rolePermission);
//            });
//            roleRepository.save(userRole);
//        }
//    }
//}
