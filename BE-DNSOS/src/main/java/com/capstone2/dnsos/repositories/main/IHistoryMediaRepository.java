package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IHistoryMediaRepository extends JpaRepository<HistoryMedia, Long> {

    HistoryMedia findByHistory(History history);

    Optional<HistoryMedia> findByHistoryHistoryId(Long historyId);
}
