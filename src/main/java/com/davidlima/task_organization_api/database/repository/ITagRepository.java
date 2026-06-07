package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITagRepository extends JpaRepository<Tag, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Tag> findByNameIgnoreCase(String name);

    List<Tag> findByIdIn(Collection<UUID> ids);
}
