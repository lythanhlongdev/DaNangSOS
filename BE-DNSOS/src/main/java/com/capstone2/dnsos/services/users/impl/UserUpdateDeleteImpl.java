package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.Family;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.IFamilyRepository;
import com.capstone2.dnsos.repositories.IRoleRepository;
import com.capstone2.dnsos.repositories.IUserRepository;
import com.capstone2.dnsos.responses.UserResponses;
import com.capstone2.dnsos.services.users.IUserUpdateDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UserUpdateDeleteImpl implements IUserUpdateDeleteService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;
    @Override
    public UserResponses updateUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        User newUser = User.builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .fullName(userDTO.getFullName())
                .cccdOrPassport(userDTO.getPassport())
                .password(userDTO.getPassword())
                .birthday(userDTO.getBirthday())
                .address(userDTO.getAddress())
                .build();
        if (!userDTO.getFamilyPhoneNumber().isEmpty()) {
            Family family = familyRepository.save(new Family());
            existingUser.setFamily(family);
        }
        List<User> families = userRepository.findByFamily(existingUser.getFamily());
        User updateUser = userRepository.save(existingUser);
        return UserResponses.mapper(updateUser, families);
    }
    @Override
    public User updateSecurityCode(SecurityDTO securityDTO) throws Exception {
        String phoneNumber = securityDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        long securityCode = Long.parseLong(securityDTO.getSecurityCode());
        existingUser.setSecurityCode(securityCode);
        return userRepository.save(existingUser);
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return false;
    }
}
