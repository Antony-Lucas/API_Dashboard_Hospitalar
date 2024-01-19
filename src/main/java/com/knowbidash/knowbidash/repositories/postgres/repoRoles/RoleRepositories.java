package com.knowbidash.knowbidash.repositories.postgres.repoRoles;

import com.knowbidash.knowbidash.entities.postgres.role.Role;
import com.knowbidash.knowbidash.roles.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleRepositories extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
