package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IHistoryMediaRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.HistoryUserResponses;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import com.capstone2.dnsos.services.histories.IHistoryCreateDeleteService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private  static final  String PATH = "./data";
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryCreateDeleteService.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception {
        // 1.check user
        User existingUser = userRepository.findByPhoneNumber(historyDTO.getUserPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + historyDTO.getUserPhoneNumber()));

        // 2. get all  rescue station
        List<RescueStation> rescueStationList = rescueStationRepository.findAll();

        // 3. get gps for user
        GPS gpsUser = this.gpsBuilder(historyDTO);

        // 4. Calculate list km
        List<KilometerMin> listKilometerMin = CalculateDistance.calculateDistance(gpsUser, rescueStationList);
        // 5. get km min { id,name, km}
        KilometerMin  kilometerMin = sortAndSearchMin(listKilometerMin);
        // 6. get rescueStation in 5. kilometerMin
        RescueStation rescueStation = rescueStationRepository.findById(kilometerMin.getRescueStationID())
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + kilometerMin.getRescueStationID()));

        // 7. create new history
        History newHistory = this.historyBuilder(existingUser,rescueStation,gpsUser,historyDTO);
        History history = historyRepository.save(newHistory);

        // 8. create history media
        HistoryMedia media = HistoryMedia.builder().history(history).build();
        historyMedia.save(media);

        // 9. create log
        changeLogService.createLog(history,"CREATE");

        //  10. save listKilometerMin in file  {./data, List  ,historyId}
        FileUtil.writeToFile(PATH,listKilometerMin, newHistory.getId().toString());
        return HistoryUserResponses.mapperHistoryAndKilometers(history, kilometerMin);
    }


    private  History historyBuilder (User existingUser, RescueStation rescueStation, GPS gps, HistoryDTO historyDTO){
        return History.builder()
                .user(existingUser)
                .rescueStation(rescueStation)
                .latitude(gps.getLatitude())
                .longitude(gps.getLongitude())
                .note(historyDTO.getNote())
                .status(Status.SYSTEM_RECEIVED)
                .build();
    }

    private  GPS gpsBuilder(HistoryDTO historyDTO){
        return  GPS.builder()
                .latitude(historyDTO.getLatitude())
                .longitude(historyDTO.getLongitude())
                .build();
    }

    private  KilometerMin sortAndSearchMin(List<KilometerMin> kilometerMin){
        return  kilometerMin.stream()
                .min(Comparator.comparingDouble(KilometerMin::getKilometers))
                .orElseThrow(() -> new RuntimeException("No rescue station found"));
    }
}
