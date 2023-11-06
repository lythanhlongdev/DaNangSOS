package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
