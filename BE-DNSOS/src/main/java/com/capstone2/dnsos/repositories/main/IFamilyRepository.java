package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Family;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
@Qualifier("MainDatabaseConfig")
public interface IFamilyRepository extends JpaRepository<Family,Long> {
}
