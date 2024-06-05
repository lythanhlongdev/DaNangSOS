package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.RescueStationRescueWorker;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("MainDatabaseConfig")
public interface IRescueStationRescueWorkerRepository extends JpaRepository<RescueStationRescueWorker, Long> {

    Page<Rescue> findAllByRescueStation(RescueStation rescueStation, Pageable pageable);

    Page<RescueStationRescueWorker> findAllRescueStationWorkersByRescueStation(RescueStation rescueStation, Pageable pageable);

    List<RescueStationRescueWorker> findAllByRescueStationAndIsActivity(RescueStation rescueStation, boolean isActivity);

    List<RescueStationRescueWorker> findAllByRescueStationAndRescue(RescueStation rescueStation, Rescue rescue);

    Optional<RescueStationRescueWorker> findByRescueStationAndRescue_IdAndIsActivity(RescueStation rescueStation, Long rescue, boolean isActivity);

    Optional<RescueStationRescueWorker> findByRescueStationAndRescue_Id(RescueStation rescueStation, Long rescue);

    RescueStationRescueWorker findByRescueAndIsActivity(Rescue rescue, boolean isActivity);
    RescueStationRescueWorker findByRescue(Rescue rescue);

    List<RescueStationRescueWorker> findAllByRescue(Rescue rescue);
}
