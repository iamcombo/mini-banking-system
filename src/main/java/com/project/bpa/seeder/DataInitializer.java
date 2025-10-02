package com.project.bpa.seeder;

import com.project.bpa.authentication.enums.PermissionCategoryEnum;
import com.project.bpa.authentication.role.entity.Permission;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.role.entity.RolePermission;
import com.project.bpa.authentication.role.repository.PermissionRepository;
import com.project.bpa.authentication.role.repository.RoleRepository;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @PostConstruct
    @Transactional
    public void init() {

        // Create default roles if they don't exist
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").description("Administrator with full access").build()));

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").description("User with basic banking operations").build()));

        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("MANAGER").description("Manager with supervisory access").build()));

        // Create default permissions if they don't exist
        List<String> permissionNames = Arrays.asList(
                "CREATE_USER", "MANAGE_USERS",
                "CREATE_ACCOUNT", "VIEW_ALL_ACCOUNTS",
                "TRANSFER", "VIEW_TRANSACTIONS",
                "APPROVE_LARGE_TRANSACTIONS",
                "GENERATE_REPORTS"
        );

        for (String permissionName : permissionNames) {
            if (!permissionRepository.existsByName(permissionName)) {
                Permission permission = new Permission();
                permission.setName(permissionName);
                permission.setDescription("Allows " + permissionName.replace(":", " "));
                permission.setCategory(PermissionCategoryEnum.USER_MANAGEMENT);
                permissionRepository.save(permission);
            }
        }

        // Assign all permissions to ADMIN role
        if (adminRole.getPermissions().isEmpty()) {
            permissionRepository.findAll().forEach(adminRole::addPermission);
            roleRepository.save(adminRole);
        }

        // Assign basic permissions to USER role
        if (userRole.getPermissions().isEmpty()) {
            List<String> userPermissions = Arrays.asList("TRANSFER", "VIEW_TRANSACTIONS", "CREATE_ACCOUNT", "VIEW_ALL_ACCOUNTS");
            permissionRepository.findByNameIn(userPermissions).forEach(userRole::addPermission);
            roleRepository.save(userRole);
        }

        // Assign permissions to MANAGER role
        if (managerRole.getPermissions().isEmpty()) {
            List<String> managerPermissions = Arrays.asList("CREATE_USER", "MANAGE_USERS", "CREATE_ACCOUNT", "VIEW_ALL_ACCOUNTS", "TRANSFER", "VIEW_TRANSACTIONS", "APPROVE_LARGE_TRANSACTIONS", "GENERATE_REPORTS");
            permissionRepository.findByNameIn(managerPermissions).forEach(managerRole::addPermission);
            roleRepository.save(managerRole);
        }

        // Create Admin User
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .email("admin@example.com")
                .role(adminRole)
                .build();
            userRepository.save(adminUser);
        }
    }
}
