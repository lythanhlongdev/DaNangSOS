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
@Table(name = "rescue_stations")
public class RescueStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rescueStationsId;

    @Column(name = "rescue_stations_name", nullable = false, length = 30)
    private String rescueStationsName;

    @Column(name = "captain", nullable = false, length = 60)
    private String captain;

    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

