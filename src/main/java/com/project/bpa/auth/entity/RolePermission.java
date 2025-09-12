package com.project.bpa.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RolePermissionId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private Permission permission;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    /**
     * Composite key for RolePermission entity.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RolePermissionId implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        @Column(name = "role_id")
        private Long roleId;
        
        @Column(name = "permission_id")
        private Long permissionId;
    }
}
