package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Comment;
import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.database.model.Roles;
import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.database.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

final class MapperUtils {

    private MapperUtils() {
    }

    static Person personReference(UUID id) {
        return id == null ? null : Person.builder().id(id).build();
    }

    static User userReference(UUID id) {
        return id == null ? null : User.builder().id(id).build();
    }

    static Project projectReference(UUID id) {
        return id == null ? null : Project.builder().id(id).build();
    }

    static Task taskReference(UUID id) {
        return id == null ? null : Task.builder().id(id).build();
    }

    static List<Tag> tagReferences(List<UUID> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .map(id -> Tag.builder().id(id).build())
                .toList();
    }

    static List<Comment> commentReferences(List<UUID> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .map(id -> Comment.builder().id(id).build())
                .toList();
    }

    static Set<Roles> roleReferences(Set<UUID> ids) {
        if (ids == null) {
            return Collections.emptySet();
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .map(id -> Roles.builder().id(id).build())
                .collect(java.util.stream.Collectors.toSet());
    }

    static UUID personId(Person person) {
        return person == null ? null : person.getId();
    }

    static String personFullName(Person person) {
        if (person == null) {
            return null;
        }
        String name = person.getName() == null ? "" : person.getName();
        String surname = person.getSurname() == null ? "" : person.getSurname();
        String fullName = (name + " " + surname).trim();
        return fullName.isEmpty() ? null : fullName;
    }

    static UUID userId(User user) {
        return user == null ? null : user.getId();
    }

    static UUID projectId(Project project) {
        return project == null ? null : project.getId();
    }

    static UUID taskId(Task task) {
        return task == null ? null : task.getId();
    }

    static List<UUID> tagIds(List<Tag> tags) {
        if (tags == null) {
            return Collections.emptyList();
        }
        return tags.stream()
                .filter(Objects::nonNull)
                .map(Tag::getId)
                .filter(Objects::nonNull)
                .toList();
    }

    static List<UUID> commentIds(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream()
                .filter(Objects::nonNull)
                .map(Comment::getId)
                .filter(Objects::nonNull)
                .toList();
    }

    static List<UUID> taskIds(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .filter(Objects::nonNull)
                .map(Task::getId)
                .filter(Objects::nonNull)
                .toList();
    }

    static Set<UUID> roleIds(Set<Roles> roles) {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
                .filter(Objects::nonNull)
                .map(Roles::getId)
                .filter(Objects::nonNull)
                .collect(java.util.stream.Collectors.toSet());
    }
}
