package com.project.bpa.security;

import com.project.bpa.authentication.role.entity.Permission;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.role.entity.RolePermission;
import com.project.bpa.authentication.role.repository.PermissionRepository;
import com.project.bpa.authentication.role.repository.RoleRepository;
import com.project.bpa.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !authentication.isAuthenticated() || !(permission instanceof String)) {
            return false;
        }

        String permissionStr = (String) permission;
        return hasPrivilege(authentication, permissionStr);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || !authentication.isAuthenticated() || !(permission instanceof String)) {
            return false;
        }

        String permissionStr = (String) permission;
        // Extend for target-specific checks if needed (e.g., ownership)
        return hasPrivilege(authentication, permissionStr);
    }

    /**
     * Checks if the authenticated user has the specified permission.
     * Defensive against lazy loading issues and concurrency.
     */
    @Transactional(readOnly = true)
    public boolean hasPrivilege(Authentication auth, String permission) {
        if (auth == null) {
            throw new IllegalArgumentException("Authentication cannot be null");
        }
        if (permission == null || permission.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission cannot be null or empty");
        }

        // Quick permission existence check (optional, for robustness)
        if (!permissionRepository.existsByName(permission)) {
            logger.warn("Permission not found: {}", permission);
            return false;
        }

        try {
            // Check direct permission
            if (hasDirectPermission(auth, permission)) {
                logger.debug("Direct permission granted: {}", permission);
                return true;
            }

            // Check role-based permissions
            boolean hasRolePermission = hasRoleBasedPermission(auth, permission);
            logger.debug("Role-based permission {} granted: {}", permission, hasRolePermission);
            return hasRolePermission;

        } catch (LazyInitializationException e) {
            logger.warn("Lazy initialization failed for permission check: {}", permission, e);
            return false;  // Graceful fallback
        } catch (Exception e) {
            logger.error("Unexpected error checking permission: {}", permission, e);
            throw new UnauthorizedException("Failed to verify permission: " + permission);
        }
    }

    private boolean hasDirectPermission(Authentication auth, String permission) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(permission));
    }

    private boolean hasRoleBasedPermission(Authentication auth, String permission) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5))  // Clean extraction
                .anyMatch(roleName -> checkRolePermissions(roleName, permission));
    }

    private boolean checkRolePermissions(String roleName, String permission) {
        // Use JOIN FETCH for single-query load (avoids lazy init entirely)
        Optional<Role> roleOpt = roleRepository.findByNameWithPermissions(roleName);
        if (roleOpt.isEmpty()) {
            logger.debug("Role not found: {}", roleName);
            return false;
        }

        Role role = roleOpt.get();

        // Fallback: If using basic findByName, defensive copy prevents CME
        // List<RolePermission> rolePerms = new ArrayList<>(role.getRolePermissions());

        return role.getPermissions().stream()
                .map(Permission::getName)
                .anyMatch(permission::equals);
    }
}