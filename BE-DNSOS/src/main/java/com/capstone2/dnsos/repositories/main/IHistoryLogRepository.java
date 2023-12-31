package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IHistoryLogRepository extends JpaRepository<HistoryLog, Long> {
    Optional<HistoryLog> findByHistory(History history);

    List<HistoryLog> findAllByHistory(History history);

}