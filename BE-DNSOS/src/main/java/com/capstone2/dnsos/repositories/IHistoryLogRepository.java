package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IHistoryLogRepository extends JpaRepository<HistoryLog, Long> {
    Optional<HistoryLog> findByHistory(History history);

    List<HistoryLog> findAllByHistory(History history);

}