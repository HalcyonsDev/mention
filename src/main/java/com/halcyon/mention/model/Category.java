package com.halcyon.mention.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "title")
    @NonNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @JsonManagedReference
    private TaskList list;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}