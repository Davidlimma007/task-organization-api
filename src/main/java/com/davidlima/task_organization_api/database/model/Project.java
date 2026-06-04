package com.davidlima.task_organization_api.database.model;

import com.davidlima.task_organization_api.enums.StatusProject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.scheduling.config.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> taskEntities;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_tag",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProject status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateEnd;
}
