package com.capstone2.dnsos.models;

import com.capstone2.dnsos.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "histories")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;
//    @Column(name = "GPS", nullable = false)
//    private Point GPS;
    @Column(name = "latitude")
    private Double latitude;// vi do

    @Column(name = "longitude")
    private Double longitude;// kinh do

    @Column(name = "voice",length = 1024)
    private String voice;
    @Column(name = "note", length = 500)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "image1",length = 1024)
    private String image1;
    @Column(name = "image2",length = 1024)
    private String image2;
    @Column(name = "image3",length = 1024)
    private String image3;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "rescue_stations_id", nullable = false)
    private RescueStation rescueStation;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
