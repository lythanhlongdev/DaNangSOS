package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Report;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("MainDatabaseConfig")
public interface IReportRepository extends JpaRepository<Report,Long> {
    List<Report> findAllByHistory_Id(Long history_id);
    boolean existsAllByHistory_Id(Long history_id);
}
