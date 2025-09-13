package com.project.bpa.authentication.role.entity;

import com.project.bpa.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new HashSet<>();

    /**
     * Helper method to add a permission to this role
     */
    public void addPermission(Permission permission) {
        RolePermission rolePermission = RolePermission.builder()
                .role(this)
                .permission(permission)
                .isActive(true)
                .build();
        rolePermission.setId(new RolePermission.RolePermissionId(this.getId(), permission.getId()));
        this.rolePermissions.add(rolePermission);
    }

    /**
     * Helper method to remove a permission from this role
     */
    public void removePermission(Permission permission) {
        RolePermission rolePermission = this.rolePermissions.stream()
                .filter(rp -> rp.getPermission().equals(permission))
                .findFirst()
                .orElse(null);
        if (rolePermission != null) {
            this.rolePermissions.remove(rolePermission);
            rolePermission.setRole(null);
        }
    }
}