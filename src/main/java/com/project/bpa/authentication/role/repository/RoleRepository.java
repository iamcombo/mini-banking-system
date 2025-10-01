package com.project.bpa.authentication.role.repository;

import com.project.bpa.authentication.role.entity.Role;
import com.project.bpa.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT DISTINCT r FROM Role r " +
            "LEFT JOIN FETCH r.permissions p " +
            "WHERE r.name = :name")
    Optional<Role> findByNameWithPermissions(@Param("name") String name);
}
