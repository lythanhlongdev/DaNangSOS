package com.capstone2.dnsos.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
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

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(name = "password", length = 12, nullable = false)
    private String password;
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;
    @Column(name = "address", nullable = false)
    private String address;
    private Date birthday;
    @Column(name = "security_code")
    private Long securityCode;
    @Column(name = "role_family", nullable = false)
    private String roleFamily;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family familyId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
