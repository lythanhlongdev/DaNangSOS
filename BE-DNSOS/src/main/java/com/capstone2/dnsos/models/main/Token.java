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
@Table(name = "tokens")
public class Token    {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", length = 255)
    private String token;

//    @Column(name = "refresh_token", length = 255)
//    private String refreshToken;

//    @Column(name = "refresh_expiration_date")
//    private LocalDateTime refreshExpirationDate;

    @Column(name = "token_type", length = 50)
    private String tokenType;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_mobile", columnDefinition = "TINYINT(1)")
    private boolean isMobile;
    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
