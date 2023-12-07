package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.RescueStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRescueStationRepository extends JpaRepository<RescueStation, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<RescueStation> findByPhoneNumber(String phoneNumber);

}
