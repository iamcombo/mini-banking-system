package com.project.bpa.auth.repository;

import com.project.bpa.auth.entity.Role;
import com.project.bpa.common.repository.BaseRepository;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
