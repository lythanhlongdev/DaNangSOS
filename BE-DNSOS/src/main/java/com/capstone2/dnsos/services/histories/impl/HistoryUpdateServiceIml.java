package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.NoteDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.HistoryByGPSResponse;
import com.capstone2.dnsos.responses.main.HistoryResponse;
import com.capstone2.dnsos.responses.main.HistoryUserResponses;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import com.capstone2.dnsos.services.histories.IHistoryUpdateService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

import java.util.*;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class HistoryUpdateServiceIml implements IHistoryUpdateService {

    private final IHistoryRepository historyRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryChangeLogService historyChangeLogService;
    private final ICancelHistoryRepository cancelHistoryRepository;
    private final IHistoryRescueRepository historyRescueRepository;
    private final IHistoryChangeLogService changeLogService;
    private final IRescueStationRescueWorkerRepository rescueStationRescueWorkerRepository;
    private static final String UPDATE = "UPDATE";
    private static final String UPDATE_STATION = "UPDATE_STATION";
    private static final String PATH = "./data";

    private final Logger logger = Logger.getLogger(getClass().getName());

    private boolean isRoleRescueWorker(User user) {
        return user.getRoles().stream().anyMatch(rcw -> rcw.getId().equals(3L));
    }

    private void validRescueWorker(User rescueWorker, History existingHistory) throws Exception {
        int checkStatus = existingHistory.getStatus().getValue();
        if (isRoleRescueWorker(rescueWorker)) {
            RescueStation rescueStation = existingHistory.getRescueStation();
            // là nhân viên và có phải của trạm không
            if (rescueStation != null) {
                boolean isWorkerInStation = rescueStation.getRescueWorkers().stream()
                        .filter(RescueStationRescueWorker::isActivity)
                        .map(RescueStationRescueWorker::getRescue)
                        .anyMatch(rcw -> rcw.getId().equals(rescueWorker.getRescues().getId()));
                if (!isWorkerInStation) {
                    throw new InvalidParamException("Không thể thay đổi trạng thái vì bạn không phải nhân viên của trạm: " + rescueStation.getRescueStationsName());
                } else {
                    if (checkStatus == Status.SYSTEM_RECEIVED.getValue()) {
                        throw new InvalidParamException("Trạm cứu hộ chưa xác nhận bạn không có quyền cập nhật trạng thái");
                    } else if (checkStatus >= 4) {
                        throw new InvalidParamException("Tín hiệu cứu hộ đã hoàn thành bạn không thể cập nhật trạng thái");
                    }
                }
            } else {
                throw new NullPointerException("Null pointer,Class: HistoryUpdateServiceIml.validRescueWorker(): 54");
            }
        }
    }

    /* Hieu nang doan nay => Set Lazy or EGGE in history
          Bug: nếu bật nhiều quyền lên thì sao vừa resuce station vừa là worker đoạn này sẽ chết
          Bug: Cùng trạm vẫn có thể đổi trược trạng thái => hị vọng Thịnh không mò ra lỗi này
    */
    @Override
    public Status updateHistoryStatus(StatusDTO statusDTO) throws Exception {

        User loadUserInAuth = this.getCurrentUser();
        History existingHistory = getHistoryById(statusDTO.getHistoryId());
        int checkStatus = existingHistory.getStatus().getValue();
        // kiem tra phai nhan vien cua tram
        if (this.isRoleRescueWorker(loadUserInAuth)) {
            this.validRescueWorker(loadUserInAuth, existingHistory);
        } else {
            RescueStation rescueStation = loadUserInAuth.getRescueStation();
            if (!rescueStation.getId().equals(existingHistory.getRescueStation().getId())) {
                throw new InvalidParamException("Trạm cứu hộ của bản không có tín hiệu cầu cứu với mã: " + statusDTO.getHistoryId());
            } else if (checkStatus >= 4) {
                throw new InvalidParamException("Không thể cập nhật bởi vì tín hiệu cậu cứu trang trong trạng thái: " + existingHistory.getStatus());
            }
        }

        Status newStatus = getStatus(statusDTO, existingHistory);
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(newStatus);// it update in cache leve 1 but not save in database
        existingHistory = historyRepository.save(existingHistory);
//        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);// save log
        return existingHistory.getStatus();
    }


    private static Status getStatus(StatusDTO statusDTO, History existingHistory) throws InvalidParamException {
//        if (existingHistory.getStatus().getValue() == -1) {
//            throw new InvalidParamException("The history status cannot be updated because the lifeguard station has not been confirmed.");
//        }

        int status = statusDTO.getStatus();
        // Bug: if we are change the number in status
        if (status < 1 || status > 4) {
            throw new InvalidParamException("Tham số trạng thái chuyền vào sai");
        }
        // 1 -> 4 but enum start 1 we can status  = SYSTEM_RECEIVED, ON_THE_WAY, ARRIVED, COMPLETED
        Status newStatus = Status.values()[status];

        if (existingHistory.getStatus().getValue() >= newStatus.getValue()) {
            throw new InvalidParamException("Không thể cập nhật trạng thái " + newStatus + " bởi vì nó đã hoàn thành");
        }
        return newStatus;
    }

    @Override
    public boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception {
        History existingHistory = getHistoryById(confirmedDTO.getHistoryId());
        // Hieu nang doan nay => Set Lazy or EGGE in history
        String phoneNumber = existingHistory.getRescueStation().getUser().getPhoneNumber();

        boolean checkPhoneNumber = phoneNumber.equals(confirmedDTO.getRescuePhoneNumber());

        boolean checkStatus = existingHistory.getStatus().getValue() == -1;
        if (!checkPhoneNumber) {
            throw new InvalidParamException("Rescue station not have history with id: " + confirmedDTO.getHistoryId());
        } else if (!checkStatus) {
            throw new InvalidParamException("Cannot update to CONFIRMED because the stage has been completed");
        }
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(Status.CONFIRMED);
        existingHistory = historyRepository.save(existingHistory);
        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);
        return true;
    }


    @Override
    public void updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception {
        User loadUserInAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        History existingHistory = this.getHistoryById(cancelDTO.getHistoryId());
        Status status = existingHistory.getStatus();

        if (!existingHistory.getUser().getPhoneNumber().equals(loadUserInAuth.getPhoneNumber())) {
            throw new InvalidParamException("User not have history with id: " + cancelDTO.getHistoryId());
        } else if (status.getValue() >= 3) {
            throw new InvalidParamException("You cannot cancel because the rescue is in state: " + status);
        }
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(Status.CANCELLED_USER);
        existingHistory = historyRepository.save(existingHistory);
        HistoryCancel historyCancel = HistoryCancel.builder()
                .history(existingHistory)
                .note(cancelDTO.getNote())
                .role("USER")
                .build();
        cancelHistoryRepository.save(historyCancel);
        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);
    }

    @Override
    public HistoryResponse updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception {
        User currentUser = this.getCurrentUser();
        History existingHistory = getHistoryById(cancelDTO.getHistoryId());
        Status status = existingHistory.getStatus();
        if (this.isRoleRescueWorker(currentUser)) {
            if (status.getValue() >= 4) {
                throw new InvalidParamException("Không thể hủy tín hiệu cầu cứu vì đang ở trang thái: " + status);
            }
            HistoryRescue historyRescue = historyRescueRepository.findByHistoryAndCancel(existingHistory, false);
            if (historyRescue == null) {
                throw new InvalidParamException("Không thể hủy bời vì Bạn chưa nhận nhiệm vụ này hoặc đã có nhân viên khác đã nhận: "+existingHistory.getId());
            }
            Rescue rescue = currentUser.getRescues();
            if (!historyRescue.getRescue().getId().equals(rescue.getId())) {
                throw new InvalidParamException("Không thể hủy bởi vì nhiệm vụ này không phải của bạn !");
            }
        } else {
            // Hieu nang doan nay => Set Lazy or EGGE in history
            String phoneNumber = existingHistory.getRescueStation().getPhoneNumber1();
            if (!phoneNumber.equals(currentUser.getPhoneNumber())) {
                throw new InvalidParamException("Trạm cứu hộ của bạn không có tín hiệu cầu cứu với id: " + currentUser.getPhoneNumber());
            }
            if (status.getValue() >= 4) {
                throw new InvalidParamException("Không thể hủy tín hiệu cầu cứu vì đang ở trang thái: " + status);
            }
        }
//        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);

        existingHistory.setStatus(Status.CANCELLED);
        existingHistory = historyRepository.save(existingHistory);
        HistoryCancel historyCancel = HistoryCancel.builder()
                .history(existingHistory)
                .note(cancelDTO.getNote())
                .role("RESCUE_WORKER")
                .build();
        cancelHistoryRepository.save(historyCancel);

        return HistoryResponse.mapperResponse(existingHistory);
    }

    @Override
    public HistoryUserResponses changeRescueStation(Long historyId) throws Exception {
        History history = getHistoryById(historyId);
        if (history.getStatus().getValue() != 0) {
            throw new InvalidParameterException("This is " + history.getStatus().toString() + ", cannot change station!");
        }
        String filename = history.getId().toString();
        Long stationId = history.getRescueStation().getId();
        // index of current station
        int currentIndex = -1;
        // get all KilometerMin Objects from file path = " ./data/? "
        List<KilometerMin> listStation = FileUtil.readFromFile("./data", filename);
        // the size of the list
        int numberOfStation = listStation.size();
        // Sort in ascending
        Collections.sort(listStation, new Comparator<KilometerMin>() {
            @Override
            public int compare(KilometerMin o1, KilometerMin o2) {
                return o1.getKilometers().compareTo(o2.getKilometers());
            }
        });

        for (KilometerMin currentStation : listStation) {
            if (currentStation.getRescueStationID() == stationId) {
                currentIndex = listStation.indexOf(currentStation);
                currentIndex++;
                if (currentIndex > (numberOfStation - 1)) {
                    currentIndex = 0;
                }
                KilometerMin newStation = listStation.get(currentIndex);
                RescueStation newRescueStation = rescueStationRepository.findById(newStation.getRescueStationID())
                        .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + newStation.getRescueStationID()));

                history.setRescueStation(newRescueStation);
                historyRepository.save(history); // Consider, should return boolean or new Rescue Station
                changeLogService.createLog(history, UPDATE_STATION);

                // return current station
                HistoryUserResponses nStation = HistoryUserResponses.mapperHistoryAndKilometers(history, newStation);
                return nStation;

            }
        }

        return null;
    }

    private User loadUserInAuth() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public HistoryByGPSResponse updateHistoryGPS(GpsDTO gpsDTO) throws Exception {
        History existingHistory = getHistoryById(gpsDTO.getHistoryId());
        double latitude = gpsDTO.getLatitude();
        double longitude = gpsDTO.getLongitude();
        String phoneNumber = loadUserInAuth().getPhoneNumber();

        if (!existingHistory.getUser().getPhoneNumber().equals(phoneNumber)) {
            throw new InvalidParamException("you have no history with the id " + gpsDTO.getHistoryId());
        } else if (existingHistory.getStatus().getValue() >= 3) {
            throw new InvalidParameterException("Rescue has ended and stopped updating location, history with id: " + gpsDTO.getHistoryId());
        }
//        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);

//        double meters = GPS.calculateDistance(latitude, longitude, existingHistory.getLatitude(), existingHistory.getLongitude());
//        meters = BigDecimal.valueOf(meters).setScale(2, RoundingMode.HALF_UP).doubleValue() * 1000;
//        if (meters >= 50) {
//            existingHistory.setLatitude(gpsDTO.getLatitude());
//            existingHistory.setLongitude(gpsDTO.getLongitude());
//        }
        existingHistory.setLatitude(latitude);
        existingHistory.setLongitude(longitude);
        existingHistory = historyRepository.save(existingHistory);
        return HistoryByGPSResponse.mapperResponse(existingHistory);
    }


    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public String updateHistoryNote(NoteDTO noteDTO) throws Exception {

        User currentUser = this.getCurrentUser();
        History existingHistory = this.getHistoryById(noteDTO.getHistoryId());
        if (!existingHistory.getUser().getId().equals(currentUser.getId())) {
            throw new InvalidParamException("Bạn có không có tín hiệu cầu cứu với mã: " + noteDTO.getHistoryId());
        } else if (existingHistory.getStatus().getValue() >= Status.COMPLETED.getValue()) {
            throw new InvalidParameterException("Bạn không thể cập nhật ghi chú cho tín hiệu đã hoàn thành");
        }

        String note = existingHistory.getNote();
        if (note == null || note.isBlank()) {
            note = noteDTO.getNote();
        } else {
            note = String.format("%s, %s", existingHistory.getNote(), noteDTO.getNote());
        }
        existingHistory.setNote(note);
        historyRepository.save(existingHistory);
        return note;
    }
}
