package com.capstone2.dnsos.models;


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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cccd_or_passport", length = 30, nullable = false)
    private String cccdOrPassport;
    @Column(name = "family_id",nullable = false)
    private Long familyId;
    @Column(name = "phone_number",length = 20 ,nullable = false)
    private String phoneNumber;
    @Column(name = "password",length = 12, nullable = false)
    private String password;
    @Column(name = "full_name",length = 50, nullable = false)
    private String fullName;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "security_code", nullable = false)
    private Long securityCode;
    @Column(name = "role_family", nullable = false)
    private String roleFamily;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
