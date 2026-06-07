package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByRoles_Id(UUID roleId);

    Optional<User> findByEmailIgnoreCase(String email);
}
