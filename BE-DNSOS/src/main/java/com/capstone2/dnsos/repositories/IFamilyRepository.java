package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFamilyRepository extends JpaRepository<Family,Long> {
}
