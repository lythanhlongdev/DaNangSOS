package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.Role;
import com.capstone2.dnsos.repositories.RescueStationRepository;
import com.capstone2.dnsos.repositories.RoleRepository;
import com.capstone2.dnsos.services.IRescueStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RescueStationServiceImpl implements IRescueStationService {
    private  final RescueStationRepository rescueStationRepository;
    private  final RoleRepository roleRepository;
    @Override
    public RescueStation register(RescueStationDTO rescueStationDTO) throws Exception {
        String phoneNumber = rescueStationDTO.getPhoneNumber();
        if (rescueStationRepository.existsByPhoneNumber(phoneNumber)) {
            throw new NotFoundException("phone number already exists");
        }
        RescueStation rescueStation = RescueStation.builder()
                .name(rescueStationDTO.getRescueStationsName())
                .captain(rescueStationDTO.getCaptain())
                .phoneNumber(rescueStationDTO.getPhoneNumber())
                .password(rescueStationDTO.getPassword())
                .address(rescueStationDTO.getAddress())
                .description(rescueStationDTO.getDescription())
                .build();
        // 16.059882, 108.209734 => DTU
        rescueStation.setLatitude(16.059882); // vi do
        rescueStation.setLongitude(108.209734);// kinh do
        // set role
        Role role  = roleRepository.findById(1L);
        rescueStation.setRole(role);
        return rescueStationRepository.save(rescueStation);
    }

    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        return null;
    }
}
