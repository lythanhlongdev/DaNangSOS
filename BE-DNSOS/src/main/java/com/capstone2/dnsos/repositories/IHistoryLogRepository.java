package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryLogRepository extends JpaRepository<HistoryLog,Long> {
}
