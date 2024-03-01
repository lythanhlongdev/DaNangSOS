package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.TokenRepository;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RescueStationAuthService implements IRescueStationAuthService {
    private final IRescueStationRepository rescueStationRepository;
    private final IRoleRepository roleRepository;

    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private  final TokenRepository tokenRepository;

    @Override
    public RescueStation register(RescueStationDTO rescueStationDTO) throws Exception {
        String phoneNumber = rescueStationDTO.getPhoneNumber();
        if (rescueStationRepository.existsByPhoneNumber(phoneNumber)) {
            throw new NotFoundException("phone number already exists");
        }
        RescueStation rescueStation = Mappers.getMappers().mapperRecueStation(rescueStationDTO);
        // 16.059882, 108.209734 => DTU
        rescueStation.setLatitude(16.059882); // vi do
        rescueStation.setLongitude(108.209734);// kinh do
        // set role
        Role role = roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("Cannot find role with id: " + 1));
        rescueStation.setRole(role);
        rescueStation.setPassword(passwordEncoder.encode(rescueStation.getPassword()));
        return rescueStationRepository.save(rescueStation);
    }

    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        RescueStation exsitingRescueStation = rescueStationRepository
                .findByPhoneNumber(loginDTO.getPhoneNumber())
                .orElseThrow(()-> new NotFoundException("phone number already exists"));
        // check pass
        if (!passwordEncoder.matches(loginDTO.getPassword(), exsitingRescueStation.getPassword())){
            throw  new BadCredentialsException("Wrong phone number or password ");
        }
        // check auth
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getPhoneNumber(),loginDTO.getPassword(),exsitingRescueStation.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateTokenForRescue(exsitingRescueStation);
    }
}
