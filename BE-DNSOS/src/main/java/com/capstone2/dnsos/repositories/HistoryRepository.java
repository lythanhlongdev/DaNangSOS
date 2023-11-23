package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {
}
