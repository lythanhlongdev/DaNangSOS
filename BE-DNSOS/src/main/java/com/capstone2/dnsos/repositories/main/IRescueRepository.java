package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IRescueRepository extends JpaRepository<Rescue, Long> {

    Optional<Rescue> findByUser(User user);
}
