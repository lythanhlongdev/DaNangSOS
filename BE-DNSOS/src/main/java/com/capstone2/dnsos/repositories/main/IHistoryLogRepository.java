package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.HistoryLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("MainDatabaseConfig")
public interface IHistoryLogRepository extends JpaRepository<HistoryLog, Long> {

    List<HistoryLog> findAllByHistory_IdOrderByEventTimeAsc(Long historyId);

    boolean existsHistoryLogByHistory_Id(Long historyId);

}