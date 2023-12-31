package com.capstone2.dnsos.models.main;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
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

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "password", length = 12, nullable = false)
    private String password;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "security_code")
    private Long securityCode;

    @Column(name = "role_family", nullable = false)
    private String roleFamily;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}