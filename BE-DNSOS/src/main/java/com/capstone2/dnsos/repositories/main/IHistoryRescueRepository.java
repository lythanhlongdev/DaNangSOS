package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryRescue;
import com.capstone2.dnsos.models.main.Rescue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("MainDatabaseConfig")
public interface IHistoryRescueRepository extends JpaRepository<HistoryRescue, Long> {
    List<HistoryRescue> findByHistory(History history);

    List<HistoryRescue> findAllByRescueAndCancel(Rescue rescue, boolean cancel);
}
