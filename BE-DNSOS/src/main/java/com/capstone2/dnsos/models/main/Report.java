package com.capstone2.dnsos.models.main;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private  History history;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "description", nullable = false, length = 500)
    private  String description;

    @Column(name = "role", nullable = false, length = 20)
    private String role;
    @PrePersist
    private  void onCreated(){
        this.createdAt = LocalDateTime.now();
    }
}
