package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.enums.StatusRescueStation;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IHistoryMediaRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.CreateHistoryByUserResponses;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import com.capstone2.dnsos.services.histories.IHistoryCreateDeleteService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryCreateDeleteService implements IHistoryCreateDeleteService {

    private final IUserRepository userRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryRepository historyRepository;
    private final IHistoryChangeLogService changeLogService;
    private final IHistoryMediaRepository historyMedia;
    private static final String PATH = "./data";
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryCreateDeleteService.class);


    private  User loadUserInAth(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateHistoryByUserResponses createHistory(HistoryDTO historyDTO) throws Exception {

        User loadUserInAuth = this.loadUserInAth();
        // 1.check user
        User existingUser = this.getUser(loadUserInAuth.getPhoneNumber());

        // 2. check history not COMPLETED, CANCELLED_USER, CANCELLED
        if (!this.checkHistoryByUser(existingUser).isEmpty()) {
            throw new InvalidParamException("You cannot create a history because the previous rescue was not completed: ");
        }

        // 3. get all  rescue station not lock
//        List<RescueStation> rescueStationList = rescueStationRepository.findAllByIsActivityAndStatus(true, StatusRescueStation.ACTIVITY);
        List<RescueStation> rescueStationList = rescueStationRepository.findAll();

        // 4. get gps for user
        GPS gpsUser = this.gpsBuilder(historyDTO);

        // 5. Calculate list km
        List<KilometerMin> listKilometerMin = CalculateDistance.calculateDistance(gpsUser, rescueStationList);
        // 6. get km min { id,name, km}
        KilometerMin kilometerMin = sortAndSearchMin(listKilometerMin);
        // 7. get rescueStation in 5. kilometerMin
        RescueStation rescueStation = rescueStationRepository.findById(kilometerMin.getRescueStationID())
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + kilometerMin.getRescueStationID()));

        // 8. create new history
        History newHistory = this.historyBuilder(existingUser, rescueStation, gpsUser);
        History history = historyRepository.save(newHistory);

        // 9. create history media // sua lai
        HistoryMedia media = HistoryMedia.builder().history(history).build();
        historyMedia.save(media);

        // 10. create log
        changeLogService.createLog(history, "CREATE");

        //  11. save listKilometerMin in file  {./data, List  ,historyId}
        FileUtil.writeToFile(PATH, listKilometerMin, newHistory.getId().toString());
        return CreateHistoryByUserResponses.mapperHistoryAndKilometers(history, kilometerMin);
    }


    private History historyBuilder(User existingUser, RescueStation rescueStation, GPS gps) {
        return History.builder()
                .user(existingUser)
                .rescueStation(rescueStation)
                .latitude(gps.getLatitude())
                .longitude(gps.getLongitude())
                .status(Status.SYSTEM_RECEIVED)
                .build();
    }

    private GPS gpsBuilder(HistoryDTO historyDTO) {
        return GPS.builder()
                .latitude(historyDTO.getLatitude())
                .longitude(historyDTO.getLongitude())
                .build();
    }

    private KilometerMin sortAndSearchMin(List<KilometerMin> kilometerMin) {
        return kilometerMin.stream()
                .min(Comparator.comparingDouble(KilometerMin::getKilometers))
                .orElseThrow(() -> new RuntimeException("No rescue station found"));
    }

    private List<History> checkHistoryByUser(User user) {
        List<Status> statuses = Arrays.asList(Status.SYSTEM_RECEIVED, Status.CONFIRMED, Status.ON_THE_WAY, Status.ARRIVED);
        return historyRepository.findAllByUserAndStatusIn(user, statuses);
    }

    private User getUser(String phoneNumber) throws Exception {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + phoneNumber));
    }
}
