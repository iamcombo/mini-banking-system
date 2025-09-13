package com.project.bpa.authentication.role.repository;

import com.project.bpa.authentication.role.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    List<Permission> findByNameIn(List<String> names);
}
