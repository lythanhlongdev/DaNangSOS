package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryRescue;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import com.capstone2.dnsos.responses.main.StatisticForHistory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IHistoryRescueRepository extends JpaRepository<HistoryRescue, Long> {

    HistoryRescue findByHistoryAndCancel(History history, boolean isCancel);

    List<HistoryRescue> findAllByRescueAndCancel(Rescue rescue, boolean cancel);

    List<HistoryRescue> findByRescueAndCancel(Rescue rescue, boolean cancel);

    List<HistoryRescue> findByHistory(History history);
//    List<HistoryRescue> findByHistoryAndCancel(History history, boolean cancel);
}
