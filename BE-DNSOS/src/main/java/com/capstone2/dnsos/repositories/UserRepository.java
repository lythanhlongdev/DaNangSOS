package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByFamilyId(long familyId);

    List<User> findByFamilyId(Long familyId);
}
