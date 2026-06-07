package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRolesRepository extends JpaRepository<Roles, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Roles> findByNameIgnoreCase(String name);
}
