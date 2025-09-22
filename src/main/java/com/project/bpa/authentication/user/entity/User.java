package com.project.bpa.authentication.user.entity;

import com.project.bpa.authentication.auth.enums.UserStatusEnum;
import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.authentication.role.entity.RolePermission;
import com.project.bpa.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatusEnum status = UserStatusEnum.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }

        // Get all active permissions for the user's role
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Add role as a GrantedAuthority (prefixed with ROLE_)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));

        // Add each permission as a GrantedAuthority
        if (role.getRolePermissions() != null) {
            role.getRolePermissions().stream()
                .filter(RolePermission::isActive)
                .map(RolePermission::getPermission)
                .filter(Objects::nonNull)
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .forEach(authorities::add);
        }

        return authorities;
    }
}