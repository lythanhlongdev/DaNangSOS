package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
}