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
        Set<Rescue> listRescues = existingHistory.getRescues();
        listRescues.add(rescue);
        existingHistory.setRescues(listRescues);
        return RescueByHistoryResponse.rescueMapperHistory(existingHistory, rescue, media);
    }
//    tối ứu lại
    private Rescue getRescue(History existingHistory, User currenUser) throws InvalidParamException {
        RescueStation rescueStation = existingHistory.getRescueStation();
        Rescue rescue = currenUser.getRescues();
        if (!rescue.getRescueStation().getId().equals(rescueStation.getId())) {
            throw new InvalidParamException("Do not accept, because you are not a member of the lifeguard station: " + rescueStation.getRescueStationsName());
        } else if (existingHistory.getStatus().getValue() == 0 || existingHistory.getStatus().getValue() >= 4) {
            throw new InvalidParamException("You cannot accept responsibility because the signal is in state: " + existingHistory.getStatus());
        }
        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
        List<History> histories = historyRepository.findAllByRescueStationAndStatusNotIn(rescueStation, notInStatus);
        Optional<History> oldHistory = histories.stream()
                .filter(history -> history.getRescues().stream().anyMatch(r -> r.getId().equals(rescue.getId()))).findFirst();
        if (oldHistory.isPresent() && !oldHistory.get().getId().equals(existingHistory.getId())) {
            throw new InvalidParamException(String.format("Can't accept new quests, please complete the old quest with id: %s,  status: %s"
                    , oldHistory.get().getId(), oldHistory.get().getStatus()));
        }
        return rescue;
    }


    @Override
    public Rescue updateGPS(GpsDTO gpsDTO) throws Exception {
        return null;
    }


    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }
}
