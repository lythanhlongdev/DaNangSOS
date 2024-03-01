package com.capstone2.dnsos.models.main;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rescue_stations")
public class RescueStation implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rescue_stations_id")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.role.getRoleName().toUpperCase()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

