package com.capstone2.dnsos.repositories;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByUser(User user);

    List<History> findAllByRescueStation(RescueStation rescueStation);

    List<History> findAllByRescueStationAndStatusNotIn(RescueStation rescueStation, List<Status> status);
}
