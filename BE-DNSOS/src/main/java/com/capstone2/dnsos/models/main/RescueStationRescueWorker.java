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
@Table(name = "rescue_station_rescue_worker")
public class RescueStationRescueWorker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rescue_id")
    private Rescue rescue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rescue_station_id")
    private RescueStation rescueStation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_activity")
    private boolean isActivity;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActivity = true;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
