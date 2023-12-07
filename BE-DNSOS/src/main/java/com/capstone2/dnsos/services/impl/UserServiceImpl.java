package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.models.Family;
import com.capstone2.dnsos.models.Role;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.IFamilyRepository;
import com.capstone2.dnsos.repositories.IRoleRepository;
import com.capstone2.dnsos.repositories.IUserRepository;
import com.capstone2.dnsos.responses.FamilyResponses;
import com.capstone2.dnsos.responses.UserResponses;
import com.capstone2.dnsos.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;

    @Override
    public User login(LoginDTO loginDTO) throws Exception {
        return null;
    }

    // Bug
    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        String phoneNumber = registerDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicatedException("phone number already exists");
        }
        User newUser = User.builder()
                .phoneNumber(registerDTO.getPhoneNumber())
                .fullName(registerDTO.getFullName())
                .cccdOrPassport(registerDTO.getPassport())
                .password(registerDTO.getPassword())
                .birthday(registerDTO.getBirthday())
                .address(registerDTO.getAddress())
                .roleFamily(registerDTO.getRoleFamily())
                .build();
        if (registerDTO.getPhoneFamily().isEmpty()) {
            Family family = familyRepository.save(new Family());
            newUser.setFamily(family);
        } else {
            String familyPhone = registerDTO.getPhoneFamily();
            User existingUser = userRepository.findByPhoneNumber(familyPhone)
                    .orElseThrow(()
                            -> new NotFoundException("Cannot find family with phone number: " + familyPhone));
            newUser.setFamily(existingUser.getFamily());
        }
        // set role
        Role role = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Cannot find Role witch id: " + 1));
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

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
    public User updateSecurityCode(SecurityDTO securityDTO) throws Exception {
        String phoneNumber = securityDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        long securityCode = Long.parseLong(securityDTO.getSecurityCode());
        existingUser.setSecurityCode(securityCode);
        return userRepository.save(existingUser);
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
}
