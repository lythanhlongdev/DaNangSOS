package com.capstone2.dnsos.models;

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
@Table(name = "cancel_histories")
public class CancelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancelId;
    private String note;
    private String role;
    private LocalDateTime createAt;

    @OneToOne(fetch = FetchType.LAZY)
    private History history;

    @PrePersist
    private void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}