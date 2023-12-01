package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.RescueStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RescueStationRepository extends JpaRepository<RescueStation, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    RescueStation findByPhoneNumber(String phoneNumber);
}
