package com.halcyon.mention.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "lists")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class TaskList {
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

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    @OneToMany(mappedBy = "list")
    @JsonBackReference
    private List<Category> categories;

    @OneToMany(mappedBy = "list")
    @JsonBackReference
    private List<Task> tasks;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}