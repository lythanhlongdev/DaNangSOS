package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.RescueForAdminResponses;
import com.capstone2.dnsos.responses.main.RescueStationResponses;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RescueStationAuthService implements IRescueStationAuthService {

    private final IRescueStationRepository rescueStationRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IFamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public RescueStationResponses register(RescueStationDTO rescueStationDTO) throws Exception {
        String phoneNumber = rescueStationDTO.getPhoneNumber();

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicatedException("Phone number already exists");
        }

        User newUser = Mappers.getMappers().mapperUser(rescueStationDTO);
        String phoneFamily = rescueStationDTO.getPhoneFamily();

        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(phoneFamily)
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + phoneFamily));

        newUser.setFamily(family);
        newUser.setRoles(Set.of(
                roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 2")),
                roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")))
        );

        newUser.setPassword(passwordEncoder.encode(rescueStationDTO.getPassword()));
        newUser = userRepository.save(newUser);

        RescueStation newRescue = rescueStationRepository.save(Mappers.getMappers().mapperRecueStation(rescueStationDTO, newUser));
        return RescueStationResponses.mapFromEntity(newRescue);
    }

    @Override
    public RescueStationResponses UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception {
        if (!passwordEncoder.matches(updateRescueDTO.getPassword(), this.getCurrenUser().getPassword())) {
            throw new InvalidParamException("Invalid password");
        }
        String phoneNumber = this.getCurrenUser().getPhoneNumber();
        RescueStation existingRescue = this.getRescueStation(phoneNumber);
        existingRescue.setRescueStationsName(updateRescueDTO.getRescueStationsName());
        existingRescue.setLatitude(updateRescueDTO.getLatitude());
        existingRescue.setLongitude(updateRescueDTO.getLongitude());
        existingRescue.setPhoneNumber2(updateRescueDTO.getPhoneNumber2());
        existingRescue.setPhoneNumber3(updateRescueDTO.getPhoneNumber3());
        existingRescue.setAddress(updateRescueDTO.getRescueStationsAddress());
        existingRescue.setDescription(updateRescueDTO.getDescription());
        User existingUser = existingRescue.getUser();
        existingUser.setPassport(updateRescueDTO.getPassport());
        existingUser.setFirstName(updateRescueDTO.getFirstName());
        existingUser.setLastName(updateRescueDTO.getLastName());
        existingUser.setBirthday(updateRescueDTO.getBirthday());
        existingUser.setAddress(updateRescueDTO.getAddress());

        Family family = updateRescueDTO.getPhoneFamily().isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(updateRescueDTO.getPhoneFamily())
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + updateRescueDTO.getPhoneFamily()));

        existingUser.setFamily(family);

        existingUser.setRoleFamily(updateRescueDTO.getRoleFamily());
        existingRescue.setUser(existingUser);
        rescueStationRepository.save(existingRescue);
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
//                -> new NotFoundException("Cannot find Rescue Station with phone: " + phoneNumber));

        return RescueStationResponses.mapFromEntity(existingRescue);
    }

    @Override
    public List<RescueForAdminResponses> getAllRecue(Pageable pageable) throws Exception {
        return rescueStationRepository
                .findAll(pageable)
                .stream()
                .map(RescueForAdminResponses::mapFromEntity)
                .toList();
    }

    @Override
    public RescueStationResponses getInfoRescue() throws Exception {
        String phoneNumber = this.getCurrenUser().getPhoneNumber();
        RescueStation exisingRescue = this.getRescueStation(phoneNumber);
        return RescueStationResponses.mapFromEntity(exisingRescue);
    }

    private RescueStation getRescueStation(String phoneNumber) throws Exception {
        return rescueStationRepository.findByPhoneNumber1(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find Rescue Station with phone: " + phoneNumber));
    }


    private User getCurrenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
