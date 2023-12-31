package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.UserResponses;
import com.capstone2.dnsos.services.users.IUserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReadServiceImpl implements IUserReadService {

    private final IUserRepository userRepository;



    @Override
    public List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user witch phone number: " + phoneNumber));
        List<User> users = userRepository.findByFamily(existingUser.getFamily());
        return users.stream().map(FamilyResponses::mapperUser).toList();
    }

    @Override
    public UserResponses getUserByPhoneNumber(String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        List<User> families = userRepository.findByFamily(existingUser.getFamily());
//        UserResponses userResponses = modelMapper.map(existingUser, UserResponses.class);
//        UserResponses userResponses = UserResponses.mapper(existingUser, families);
        return UserResponses.mapper(existingUser, families);
    }

    @Override
    public boolean getSecurityCodeByPhoneNumber(SecurityDTO securityDTO) throws Exception {
        String phoneNumber = securityDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        if (existingUser.getSecurityCode() == null) {
            throw new NullPointerException("Security code is null witch phone number: " + phoneNumber);
        }
        long code = Long.parseLong(securityDTO.getSecurityCode());
        return existingUser.getSecurityCode() == code;
    }


}
