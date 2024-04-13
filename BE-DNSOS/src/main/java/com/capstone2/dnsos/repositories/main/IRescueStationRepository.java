package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IRescueStationRepository extends JpaRepository<RescueStation, Long> {

    Optional<RescueStation> findByPhoneNumber1(String phoneNumber);

    List<RescueStation> findAllByIsActivity(boolean isActivity);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsById(Long id);

    Optional<RescueStation> findByPhoneNumber(String phoneNumber);
   Optional<RescueStation> findByUser(User user);
   List<RescueStation> findAllByUser(User user);

}
