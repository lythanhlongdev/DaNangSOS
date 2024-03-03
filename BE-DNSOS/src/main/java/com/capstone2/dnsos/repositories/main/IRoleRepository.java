package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("MainDatabaseConfig")
public interface IRoleRepository extends JpaRepository<Role,Long> {
}
