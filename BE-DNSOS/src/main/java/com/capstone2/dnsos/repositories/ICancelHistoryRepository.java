package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.CancelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICancelHistoryRepository extends JpaRepository<CancelHistory, Long> {
}
