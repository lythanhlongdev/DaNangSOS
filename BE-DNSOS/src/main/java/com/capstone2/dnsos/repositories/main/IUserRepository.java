package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import com.capstone2.dnsos.responses.main.StatisticsUserActivity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Qualifier("MainDatabaseConfig")
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    List<User> findByFamilyId(Long family);

    List<User> findByFamily(Family family);

    Page<User> findAllByRoles_Id(Long roleId, Pageable pageable) throws Exception;

    @Query("SELECT new com.capstone2.dnsos.responses.main.StatisticsUserActivity(" +
            "COUNT(u), " +
            "SUM(CASE WHEN u.isActivity = true THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN u.isActivity = false THEN 1 ELSE 0 END)) " +
            "FROM User u")
    StatisticsUserActivity getUserActivity();

}
