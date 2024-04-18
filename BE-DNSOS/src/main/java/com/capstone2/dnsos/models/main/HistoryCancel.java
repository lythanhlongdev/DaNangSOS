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
@Table(name = "history_cancel")
public class HistoryCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;
    private String role;
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @OneToOne(fetch = FetchType.LAZY)
    private History history;

    @PrePersist
    private void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}
