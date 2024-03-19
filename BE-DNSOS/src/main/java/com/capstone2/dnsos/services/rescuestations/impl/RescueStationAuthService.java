package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.RescueStationResponses2;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.NotYetBoundException;
import java.util.Arrays;
import java.util.HashSet;
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
    public RescueStationResponses2 register(RescueStationDTO rescueStationDTO) throws Exception {
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
        Role roleRescue = roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 1"));
        Role roleUser = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 2"));
        newUser.setRoles(Set.of(roleUser, roleRescue));
        newUser.setPassword(passwordEncoder.encode(rescueStationDTO.getPassword()));
        newUser = userRepository.save(newUser);
        RescueStation newRescue = rescueStationRepository.save(Mappers.getMappers().mapperRecueStation(rescueStationDTO, newUser));
        return RescueStationResponses2.mapFromEntity(newRescue);
    }

    @Override
    public RescueStationResponses2 UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception {
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

        return RescueStationResponses2.mapFromEntity(existingRescue);
    }

    @Override
    public RescueStationResponses2 getInfoRescue() throws Exception {
        String phoneNumber = this.getCurrenUser().getPhoneNumber();
        RescueStation exisingRescue = this.getRescueStation(phoneNumber);
        return RescueStationResponses2.mapFromEntity(exisingRescue);
    }

    private RescueStation getRescueStation(String phoneNumber) throws Exception {
        return rescueStationRepository.findByPhoneNumber1(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find Rescue Station with phone: " + phoneNumber));
    }


    private User getCurrenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
