package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryMediaRepository extends JpaRepository<HistoryMedia, Long> {

    HistoryMedia findByHistory(History history);
}
