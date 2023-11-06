package com.capstone2.dnsos.models;


import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rescue_stations")
public class RescueStation {

    @Id
    @Column(name = "rescue_stations_id")
    private Long rescueStationsId;
    @Column(name = "rescue_stations_name", length = 30, nullable = false)
    private String name;
    @Column(name = "phone_number", length = 20,  nullable = false)
    private String phoneNumber;
    @Column(name = "password", length = 12, nullable = false)
    private String password;
    @Column(name = "description")
    private String description;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "GPS", nullable = false)
    private Point GPS;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
