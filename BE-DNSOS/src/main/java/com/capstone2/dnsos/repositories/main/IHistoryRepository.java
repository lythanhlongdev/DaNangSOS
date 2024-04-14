package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("MainDatabaseConfig")
public interface IHistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByUser(User user);

    List<History> findAllByRescueStation(RescueStation rescueStation);

    List<History> findAllByRescueStationAndStatusNotIn(RescueStation rescueStation, List<Status> status);
    List<History>  findAllByUserAndStatusIn(User user, List<Status> statuses);


}
