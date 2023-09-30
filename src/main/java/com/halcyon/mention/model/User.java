package com.halcyon.mention.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Token> tokens;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<TaskList> lists;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}