package com.capstone2.dnsos.models.main;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rescue")
public class Rescue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rescue_station_id", referencedColumnName = "id", unique = true)
    private RescueStation rescueStation;

    @OneToMany(mappedBy = "rescue", fetch = FetchType.LAZY)
    private List<HistoryRescue> HistoryRescue;
    @PrePersist
    private void oneCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
