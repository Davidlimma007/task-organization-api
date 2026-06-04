package com.davidlima.task_organization_api.database.model;

import com.davidlima.task_organization_api.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarioEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tarefa_etiqueta",
                joinColumns = @JoinColumn(name = "tarefa_id"),
                inverseJoinColumns = @JoinColumn(name = "etiqueta_id"))
    private List<Etiqueta> etiquetaEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Pessoa autor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataFinalizado;
}
