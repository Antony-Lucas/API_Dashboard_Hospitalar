package com.knowbidash.knowbidash.repositories;

import com.knowbidash.knowbidash.entities.Role;
import com.knowbidash.knowbidash.roles.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositories extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
