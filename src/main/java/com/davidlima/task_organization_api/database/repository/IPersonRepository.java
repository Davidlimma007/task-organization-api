package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository extends JpaRepository<Person, UUID> {

    boolean existsByUserId(UUID userId);

    Optional<Person> findByUserId(UUID userId);
}
