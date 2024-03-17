package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


    @Override
    public RescueStation register(RescueStationDTO rescueStationDTO) throws Exception {
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

        return rescueStationRepository.save(Mappers.getMappers().mapperRecueStation(rescueStationDTO, newUser));
    }


    private User getUserById(long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Cannot find user with id: " + userId));
    }


}
