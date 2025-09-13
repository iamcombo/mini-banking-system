package com.project.bpa.authentication.user.repository;

import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
