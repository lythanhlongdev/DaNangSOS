package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.repositories.main.TokenRepository;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RescueStationAuthService implements IRescueStationAuthService {

    private final IRescueStationRepository rescueStationRepository;
    private final IRoleRepository roleRepository;
    private  final IUserRepository userRepository;

    @Override
    public RescueStation register(RescueStationDTO rescueStationDTO) throws Exception {
        long userId = rescueStationDTO.getUserId();
        User existingUser = this.getUserById(userId);

        Set<Role> roles =  existingUser.getRoles();

        final Role rescue = roleRepository.findById(1L).orElseThrow(()-> new NotFoundException("Cannot find role with id: "+1));
        roles.add(rescue);
        existingUser.setRoles(roles);

        RescueStation newRescue = Mappers.getMappers().mapperRecueStation(rescueStationDTO, existingUser);
        // 16.059882, 108.209734 => DTU
        newRescue.setLatitude(16.059882); // vi do
        newRescue.setLongitude(108.209734);// kinh do
        // set role
        return rescueStationRepository.save(newRescue);
    }

    private  User getUserById(long userId) throws  Exception{
        return  userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("Cannot find user with id: "+userId));
    }


}
