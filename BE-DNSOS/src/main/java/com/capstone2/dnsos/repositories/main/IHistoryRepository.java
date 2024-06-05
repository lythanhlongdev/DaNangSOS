package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Qualifier("MainDatabaseConfig")
public interface IHistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByUser(User user);

    Optional<History> findByUserAndStatusNotIn(User user, List<Status> statuses);

    List<History> findAllByRescueStation(RescueStation rescueStation);

    List<History> findAllByRescueStationAndStatusNotIn(RescueStation rescueStation, List<Status> status);

    List<History> findAllByUserAndStatusIn(User user, List<Status> statuses);


    @Query("SELECT new com.capstone2.dnsos.responses.main.HistorySummaryResponse(" +
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h")
    HistorySummaryResponse getHistoryStatistics();

    @Query("SELECT new com.capstone2.dnsos.responses.main.HistorySummaryResponse(" +
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status IN ('CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h " +
            "WHERE DAY(h.createdAt) = :day " +
            "AND MONTH(h.createdAt) = :month " +
            "AND YEAR(h.createdAt) = :year")
    HistorySummaryResponse getHistoryStatisticsByDay(@Param("day") int day, @Param("month") int month, @Param("year") int year);



    @Query("SELECT new com.capstone2.dnsos.responses.main.HistorySummaryResponse(" +
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h " +
            "WHERE MONTH(h.createdAt) = :month AND " + // Đã thêm khoảng trắng ở đây
            "YEAR(h.createdAt) = :year")
    HistorySummaryResponse getHistoryStatisticsByMonth(@Param("month") int month, @Param("year") int year);



    @Query("SELECT new com.capstone2.dnsos.responses.main.HistorySummaryResponse(" +
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END))" +
            "FROM History h " +
            "WHERE YEAR(h.createdAt) = :year")
    HistorySummaryResponse getHistoryStatisticsByYear(@Param("year") int year);
}
