package com.halcyon.mention.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "title")
    @NonNull
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "complexity")
    private Complexity complexity;

    @Column(name = "status")
    private Status status;

    @Column(name = "date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @JsonManagedReference
    private TaskList list;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonManagedReference
    private Category category;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}