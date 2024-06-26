package com.capstone2.dnsos.models.main;


import com.capstone2.dnsos.enums.StatusRescueStation;
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
@Table(name = "rescue_stations")
public class RescueStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    @Column(name = "rescue_stations_name", nullable = false, length = 30)
    private String rescueStationsName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number1", nullable = false, length = 20, unique = true)
    private String phoneNumber1;

    @Column(name = "phone_number2", nullable = false, length = 20, unique = true)
    private String phoneNumber2;

    @Column(name = "phone_number3", nullable = false, length = 20, unique = true)
    private String phoneNumber3;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_activity")
    private Boolean isActivity = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusRescueStation status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        isActivity= true;
        status = StatusRescueStation.PAUSE;
    }

    @OneToMany(mappedBy = "rescueStation", fetch = FetchType.EAGER)
    List<RescueStationRescueWorker> rescueWorkers;


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}

