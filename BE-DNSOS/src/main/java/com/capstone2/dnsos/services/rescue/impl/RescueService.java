package com.capstone2.dnsos.services.rescue.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import com.capstone2.dnsos.responses.main.RescueResponse;
import com.capstone2.dnsos.services.rescue.IRescueService;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    @Override
//    public RescueByHistoryResponse getRescueByUserId(GpsDTO gpsDTO) throws Exception {
//        // Get History
//        History exitingHistory = this.getHistoryById(gpsDTO.getHistoryId());
//        // Get User In Auth
//        Rescue rescue = rescueRepository.findAllByUserId(this.userInAuth().getId());
//        // Get Rescue Station
//        RescueStation rescueStation = exitingHistory.getRescueStation();
//        HistoryMedia media = historyMediaRepository.findByHistory_Id(exitingHistory.getId()).orElse(null);
//
//        // check  Rescue in Rescue Station ?  and   0 < status < 4
//        if (rescueStation.getId() != rescue.getRescueStation().getId()) {
//            throw new InvalidParamException("Do not accept, because you are not a member of the lifeguard station: " + rescueStation.getRescueStationsName());
//        } else if (exitingHistory.getStatus().getValue() == 0 || exitingHistory.getStatus().getValue() >= 4) {
//            throw new InvalidParamException("You cannot accept responsibility because the signal is in state: " + exitingHistory.getStatus());
//        }
//        // Set GPS for Rescue
//
//        rescue.setLatitude(gpsDTO.getLatitude());
//        rescue.setLongitude(gpsDTO.getLongitude());
//        rescue = rescueRepository.save(rescue);
//        // Add Rescue for History
//        exitingHistory.setRescues(Set.of(rescue));
//        exitingHistory = historyRepository.save(exitingHistory);
//        RescueByHistoryResponse response = RescueByHistoryResponse.rescueMapperHistory(exitingHistory,rescue,media);
//        // Mapper
//        return response;
//    }

    @Transactional
    public RescueByHistoryResponse getRescueByUserId(GpsDTO gpsDTO) throws Exception {

        return  null;
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
