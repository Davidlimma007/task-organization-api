package com.davidlima.task_organization_api.database.model;

import com.davidlima.task_organization_api.enums.StatusTask;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_tag",
                joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTask status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateEnd;
}
