package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.enums.StatusRescueStation;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos;
import com.capstone2.dnsos.responses.main.StatusRescueStationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Qualifier("MainDatabaseConfig")
public interface IRescueStationRepository extends JpaRepository<RescueStation, Long> {

    Optional<RescueStation> findByPhoneNumber1(String phoneNumber);

    List<RescueStation> findAllByIsActivityAndStatus(boolean isActivity, StatusRescueStation status);

    boolean existsById(Long id);

    Optional<RescueStation> findByUser(User user);

    List<RescueStation> findAllByUser(User user);

    @Query("SELECT new com.capstone2.dnsos.responses.main.StatusRescueStationResponse(" +
            "COUNT(rc) AS Total, " +
            "SUM(CASE WHEN rc.status = 'ACTIVITY' THEN 1 ELSE 0 END) AS Is_Online, " +
            "SUM(CASE WHEN rc.status = 'PAUSE' THEN 1 ELSE 0 END) AS Is_Offline, " +
            "SUM(CASE WHEN rc.status = 'OVERLOAD' THEN 1 ELSE 0 END) AS Overload) " +
            "FROM RescueStation AS rc")
    StatusRescueStationResponse totalStatusRescueStation();

    @Query("SELECT new com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos(" +
            "rc.id, " +
            "rc.rescueStationsName, " + // Thêm dấu phẩy sau rc.rescueStationsName
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h JOIN h.rescueStation rc " + // Thêm tên thuộc tính của quan hệ giữa History và RescueStation
            "GROUP BY rc.id, rc.rescueStationsName")
        // Thêm GROUP BY cho rc.id và rc.rescueStationsName
    List<StatisticsRescueStationHaveSos> getRescueStationHaveSos();


    @Query("SELECT new com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos(" +
            "rc.id, " +
            "rc.rescueStationsName, " + // Thêm dấu phẩy sau rc.rescueStationsName
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h JOIN h.rescueStation rc " +
            "WHERE DATE(h.createdAt) = :date " + // Thêm tên thuộc tính của quan hệ giữa History và RescueStation
            "GROUP BY rc.id, rc.rescueStationsName")
        // Thêm GROUP BY cho rc.id và rc.rescueStationsName
    List<StatisticsRescueStationHaveSos> getRescueStationHaveSosByDate(@Param("date") LocalDate date);


    @Query("SELECT new com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos(" +
            "rc.id, " +
            "rc.rescueStationsName, " + // Thêm dấu phẩy sau rc.rescueStationsName
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h JOIN h.rescueStation rc " + // Thêm tên thuộc tính của quan hệ giữa History và RescueStation
            "WHERE MONTH(h.createdAt) = :month AND " + // Thêm khoảng trắng ở cuối dòng
            "YEAR(h.createdAt) = :year " + // Thêm khoảng trắng ở cuối dòng và dấu phẩy ở cuối
            "GROUP BY rc.id, rc.rescueStationsName")
        // Thêm GROUP BY cho rc.id và rc.rescueStationsName
    List<StatisticsRescueStationHaveSos> getRescueStationHaveSosByMoth(@Param("month") int month, @Param("year") int year);


    @Query("SELECT new com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos(" +
            "rc.id, " +
            "rc.rescueStationsName, " +
            "COUNT(h), " +
            "SUM(CASE WHEN h.status NOT IN ('COMPLETED', 'CANCELLED_USER', 'CANCELLED') THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' or h.status = 'CANCELLED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED_USER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.status = 'CANCELLED' THEN 1 ELSE 0 END)) " +
            "FROM History h JOIN h.rescueStation rc " +
            "WHERE YEAR(h.createdAt) = :year " +
            "GROUP BY rc.id, rc.rescueStationsName")
        // Thêm khoảng trắng trước chuỗi "GROUP BY"
    List<StatisticsRescueStationHaveSos> getRescueStationHaveSosByYear(@Param("year") int year);


}
