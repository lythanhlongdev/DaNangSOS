package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByFamily(Family family);
    List<User> findByFamily(Family family);
}
