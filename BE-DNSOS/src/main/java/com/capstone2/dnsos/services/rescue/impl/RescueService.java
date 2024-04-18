package com.capstone2.dnsos.services.rescue.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.HistoryByGPSResponse;
import com.capstone2.dnsos.responses.main.HistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import com.capstone2.dnsos.responses.main.RescueResponse;
import com.capstone2.dnsos.services.rescue.IRescueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RescueService implements IRescueService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;
    private final IRescueRepository rescueRepository;
    private final PasswordEncoder passwordEncoder;
    private final IHistoryRepository historyRepository;
    private final IHistoryRescueRepository historyRescueRepository;
    private final IHistoryMediaRepository historyMediaRepository;

    private User userInAuth() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public RescueResponse register(RegisterDTO registerDTO) throws Exception {
        if (userRepository.existsByPhoneNumber(registerDTO.getPhoneNumber())) {
            throw new DuplicatedException("phone number already exists");
        }
        // Mapper
        User newUser = Mappers.getMappers().mapperUser(registerDTO);
        Set<Role> roles = Set.of(
                roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")),
                roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 4"))

        );

        // Set Role
        String phoneFamily = registerDTO.getPhoneFamily();
        newUser.setRoles(roles);
        // Set Family
        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(phoneFamily)
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + phoneFamily));
        newUser.setFamily(family);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // save user
        newUser = userRepository.save(newUser);

        Rescue newRescue = Rescue.builder()
                .user(newUser)
                .rescueStation(this.userInAuth().getRescueStation())
                .build();
        // Create Rescue and create, mapper response
        newRescue = rescueRepository.save(newRescue);
        return RescueResponse.rescueMapperResponse(newRescue);
    }

    // Lỗi chỗ này
//    private History checkQRScanningCondition(Rescue rescue) {
//        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
//        return historyRepository.findByRescuesAndStatusNotIn(Set.of(rescue), notInStatus);
//    }

    @Transactional
    public RescueByHistoryResponse scanQrCode(GpsDTO gpsDTO) throws Exception {
        History existingHistory = this.getHistoryById(gpsDTO.getHistoryId());
        User currenUser = this.userInAuth();
        Rescue rescue = this.getRescue(existingHistory, currenUser);
        HistoryMedia media = historyMediaRepository.findByHistory_Id(existingHistory.getId()).orElse(null);
        rescue.setLongitude(gpsDTO.getLongitude());
        rescue.setLatitude(gpsDTO.getLatitude());
        rescue = rescueRepository.save(rescue);
        HistoryRescue historyRescue = HistoryRescue.builder()
                .history(existingHistory)
                .rescue(rescue)
                .build();
        historyRescue = historyRescueRepository.save(historyRescue);
        return RescueByHistoryResponse.rescueMapperHistory(existingHistory, rescue, media);
    }
//    tối ứu lại


    private Rescue getRescue(History existingHistory, User currenUser) throws Exception {
        RescueStation rescueStation = existingHistory.getRescueStation();
        Rescue rescue = currenUser.getRescues();
        //   khác trạm không cho quét
        //   nếu trạng thái lớn hơn hoặc bằng 4 không cho quét
        if (!rescue.getRescueStation().getId().equals(rescueStation.getId())) {
            throw new InvalidParamException("Do not accept, because you are not a member of the lifeguard station: " + rescueStation.getRescueStationsName());
        } else if (existingHistory.getStatus().getValue() == 0 || existingHistory.getStatus().getValue() >= 4) {
            throw new InvalidParamException("You cannot accept responsibility because the signal is in state: " + existingHistory.getStatus());
        }


        // kiếm tra xem nếu như nhiệm vụ cũ chưa hoàn thành mã đã quyets nhiệm vụ mới thì sẽ hủy nhiệm vụ cũ đi
        List<HistoryRescue> checkHistoryRescue = historyRescueRepository.findAllByRescueAndCancel(rescue, false);
        if (!checkHistoryRescue.isEmpty()) {
            boolean checkUpdate = false;
            for (HistoryRescue item : checkHistoryRescue) {
                // Kiểm tra xem history của item có tồn tại không
                History history = item.getHistory();
                if (history != null) {
                    // Kiểm tra xem status của history có hợp lệ không
                    int statusValue = history.getStatus().getValue();
                    if (statusValue > 0 && statusValue < 4) {
                        // Cập nhật cancel thành true
                        item.setCancel(true);
                        checkUpdate = true;
                    }
                }
            }
            if (checkUpdate) {
                historyRescueRepository.saveAllAndFlush(checkHistoryRescue);
            }
        }


        return rescue;
    }

//    private Rescue getRescue(History existingHistory, User currenUser) throws InvalidParamException {
//        RescueStation rescueStation = existingHistory.getRescueStation();
//        Rescue rescue = currenUser.getRescues();
//        //   khác trạm không cho quét
//        //   nếu trạng thái lớn hơn hoặc bằng 4 không cho quét
//        if (!rescue.getRescueStation().getId().equals(rescueStation.getId())) {
//            throw new InvalidParamException("Do not accept, because you are not a member of the lifeguard station: " + rescueStation.getRescueStationsName());
//        } else if (existingHistory.getStatus().getValue() == 0 || existingHistory.getStatus().getValue() >= 4) {
//            throw new InvalidParamException("You cannot accept responsibility because the signal is in state: " + existingHistory.getStatus());
//        }
//        // Đoạn code sau đã bị bỏ do ý kiến của Thịnh cho phép quét nhiệm vụ mới mà không cần hoàn thành nhiệm vụ cũ :))
////        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
////        List<History> histories = historyRepository.findAllByRescueStationAndStatusNotIn(rescueStation, notInStatus);
////        Optional<History> oldHistory = histories.stream()
////                .filter(history -> history.getRescues().stream().anyMatch(r -> r.getId().equals(rescue.getId()))).findFirst();
////        // Nếu chua hoành thành nhiệm vụ cũ không cho quyeets cái mới
////        if (oldHistory.isPresent() && !oldHistory.get().getId().equals(existingHistory.getId())) {
////            throw new InvalidParamException(String.format("Can't accept new quests, please complete the old quest with id: %s,  status: %s"
////                    , oldHistory.get().getId(), oldHistory.get().getStatus()));
////        }
//        return rescue;
//    }


    @Override
    public RescueByHistoryResponse updateGPS(GpsDTO gpsDTO) throws Exception {
        User currentUser = this.userInAuth();
        History existingHistory = this.getHistoryById(gpsDTO.getHistoryId());
        Rescue existingRescue = this.getRescueByUser(currentUser);
        boolean checkRescueInHistory = existingHistory.getHistoryRescue()
                .stream()
                .filter(hr -> !hr.isCancel())
                .map(HistoryRescue::getRescue)
                .anyMatch(rcw -> rcw.getId().equals(existingRescue.getId()));
        if (!checkRescueInHistory) {
            throw new NotFoundException("You don't have a rescue mission with id: " + existingRescue.getId());
        } else if (existingHistory.getStatus().getValue() >= 4) {
            throw new InvalidParamException("You cannot update GPS because the history is in the status: " + existingHistory.getStatus());
        }
        existingRescue.setLatitude(gpsDTO.getLatitude());
        existingRescue.setLongitude(gpsDTO.getLongitude());
        rescueRepository.save(existingRescue);
        HistoryMedia media = historyMediaRepository.findByHistory(existingHistory);
        return RescueByHistoryResponse.rescueMapperHistory(existingHistory, existingRescue, media);
    }


    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

    private Rescue getRescueByUser(User currentUser) throws NotFoundException {
        return rescueRepository.findByUser(currentUser).orElseThrow(
                () -> new NotFoundException("Cannot find rescue with phone number: " + currentUser.getPhoneNumber()));
    }

}