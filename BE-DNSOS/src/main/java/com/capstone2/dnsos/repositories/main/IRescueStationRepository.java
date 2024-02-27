package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.RescueStation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IRescueStationRepository extends JpaRepository<RescueStation, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsById(Long id);

    Optional<RescueStation> findByPhoneNumber(String phoneNumber);


}
