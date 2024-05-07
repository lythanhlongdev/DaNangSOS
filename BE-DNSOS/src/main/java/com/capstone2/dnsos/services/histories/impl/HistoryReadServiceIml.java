package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.histories.IHistoryReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Service
public class HistoryReadServiceIml implements IHistoryReadService {

    private final IUserRepository userRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryRepository historyRepository;
    private final IHistoryMediaRepository historyMedia;
    private final IHistoryRescueRepository historyRescueRepository;


    private User getCurrenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<HistoryByUserResponses> getAllHistoryByUser() throws Exception {

        User loadUserInAuth = this.getCurrenUser();
        List<History> historiesByUser = historyRepository.findAllByUser(loadUserInAuth);
        return historiesByUser.stream().map((history) ->
                HistoryByUserResponses.mapFromEntities(history, historyMedia.findByHistory(history))).toList();
    }


    @Override
    public List<HistoryByRescueStationResponses> getAllHistoryByRescueStation() throws Exception {
        // check user
        User loadUserInAuth = this.getCurrenUser();
        // check user have rescue
        RescueStation rescueStation = this.getRescueByUser(loadUserInAuth);

        List<History> histories = historyRepository.findAllByRescueStation(rescueStation);
        return histories.stream().map(this::mapFromEntities).toList();
    }

    @Override
    public List<HistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancel() throws Exception {
        User loadUserInAuth = this.getCurrenUser();
        // check user have rescue
        RescueStation rescueStation = this.getRescueByUser(loadUserInAuth);
        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
        List<History> histories = historyRepository.findAllByRescueStationAndStatusNotIn(rescueStation, notInStatus);
        return histories.stream().map(this::mapFromEntities).toList();
    }

    public HistoryByRescueStationResponses mapFromEntities(History history) {
        HistoryMedia medias = historyMedia.findByHistory(history);
        User user = history.getUser();
        List<User> families = userRepository.findByFamilyId(user.getFamily().getId());
        return HistoryByRescueStationResponses.builder()
                .status(history.getStatus())
                .historyId(history.getId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .historyMediaResponses(HistoryMediaResponses.mapFromEntity(medias))
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .userNotPasswordResponses(UserNotPasswordResponses.mapper(user, families))
                .build();
    }

    // v 1.1.0
//    public HistoryByRescueStationResponsesv_1_1_0 mapFromEntities(History history) {
//        HistoryMedia medias = historyMedia.findByHistory(history);
//        return HistoryByRescueStationResponsesv_1_1_0.builder()
//                .status(history.getStatus())
//                .historyId(history.getId())
//                .latitude(history.getLatitude())
//                .longitude(history.getLongitude())
//                .note(history.getNote())
//                .img1(medias.getImage1())
//                .img2(medias.getImage2())
//                .img3(medias.getImage3())
//                .voice(medias.getVoice())
//                .createdAt(history.getCreatedAt().toString())
//                .updatedAt(history.getUpdatedAt().toString())
//                .userResponses(UserNotPasswordResponses.mapper(history.getUser()))
//                .build();
//    }

//    private HistoryByRescueStationResponses mapToResponse(History history) {
//        HistoryMedia medias = historyMedia.findByHistory(history);
//        Family family = history.getUser().getFamily();
//        List<User> families = userRepository.findByFamily(family);
//
//        return HistoryByRescueStationResponses.builder()
//                .status(history.getStatus())
//                .historyId(history.getId())
//                .note(history.getNote())
//                .createdAt(history.getCreatedAt())
//                .updatedAt(history.getUpdatedAt())
//                .userNotPasswordResponses(UserNotPasswordResponses.mapper(history.getUser(), families))
//                .mediaResponses(HistoryMediaResponses.mapFromEntity(medias))
//                .build();
//    }

    @Override
    public HistoryInMapAppResponse getCurrentHistoryInMapUser() throws Exception {
        User currentUser = getCurrenUser();
        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
        // đoạn này nguy hiểm lỡ ai đó vô database thay đổi trạng thánh lịch sử là toang
        History existingHistory = historyRepository.findByUserAndStatusNotIn(currentUser, notInStatus)
                .orElseThrow(
                        () -> new NotFoundException("Currently you have no signal for help")
                );

//        if (!existingHistory.getUser().getId().equals(currentUser.getId())) {
//            throw new NotFoundException("You have no history with ID: " + historyId);
//        } else if (existingHistory.getStatus().getValue() >= 4) {
//            throw new InvalidParamException("You cannot display history with status: " + existingHistory.getStatus());
//        }
      
        HistoryMedia media = historyMedia.findByHistory_Id(existingHistory.getId()).orElse(null);

        // láy trong bảng trung gian ra luôn
        HistoryRescue historyRescue = historyRescueRepository.findByHistoryAndCancel(existingHistory, false);
        if(historyRescue == null){
           return HistoryInMapAppResponse.mapperInMapNotHaveRescueWorker(existingHistory,media);
        }
        /*
          Lấy ra nhưng nhân viên đã nhận nhiệm vụ này và xuất nó map
        */
//        List<User> usersIsRecueWorker = existingHistory.getHistoryRescue()
//                .stream()
//                .filter(hr -> !hr.isCancel())
//                .map(HistoryRescue::getRescue)
//                .map(Rescue::getUser).toList();

        return HistoryInMapAppResponse.mapperInMap(existingHistory, media, historyRescue);
    }

    // 26-04-2024 Đã sửa theo ý của Thịnh
//    @Override
//    public HistoryInMapAppResponse getCurrentHistoryInMapUser(Long historyId) throws Exception {
//        User currentUser = getCurrenUser();
//        History existingHistory = getHistoryById(historyId);
//        // nếu chưa sác nhận chưa được hiện thị lên map thấy hơi cứng rắn nên bỏ qua điều kiện 1  lệnh if thứ 2
////        if (!existingHistory.getUser().getId().equals(currentUser.getId())) {
////            throw new NotFoundException("You have no history with ID: " + historyId);
////        } else if (existingHistory.getStatus().getValue() == 0 || existingHistory.getStatus().getValue() >= 4) {
////            throw new InvalidParamException("You cannot display history with status: " + existingHistory.getStatus());
////        }
//
//        if (!existingHistory.getUser().getId().equals(currentUser.getId())) {
//            throw new NotFoundException("You have no history with ID: " + historyId);
//        } else if (existingHistory.getStatus().getValue() >= 4) {
//            throw new InvalidParamException("You cannot display history with status: " + existingHistory.getStatus());
//        }
//        RescueStation rescueStation = existingHistory.getRescueStation();
//        HistoryMedia media = historyMedia.findByHistory_Id(existingHistory.getId()).orElse(null);
//        List<User> usersIsRecueWorker = existingHistory.getHistoryRescue().stream()
//                .filter(hr -> !hr.isCancel())
//                .map(HistoryRescue::getRescue)
//                .map(Rescue::getUser).toList();
//        return HistoryInMapAppResponse.mapperInMap(rescueStation, existingHistory, media, usersIsRecueWorker);
//    }


    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository.findById(historyId).orElseThrow(() ->
                new NotFoundException("Cannot find History Not Found with ID: " + historyId));
    }

    @Override
    public HistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception {
        return null;
    }

    private User getUserByPhone(String phoneNumber) throws Exception {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new NotFoundException("Cannot find user with phone number: " + phoneNumber));
    }

    private RescueStation getRescueByUser(User user) throws Exception {
        return rescueStationRepository.findByUser(user).orElseThrow(() ->
                new NotFoundException("Cannot find RescueStation with phone number: " + user.getPhoneNumber()));
    }
}
