package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.Family;
import com.capstone2.dnsos.models.Role;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.IFamilyRepository;
import com.capstone2.dnsos.repositories.IRoleRepository;
import com.capstone2.dnsos.repositories.IUserRepository;
import com.capstone2.dnsos.services.users.IUserAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUserAuthServiceImpl implements IUserAuthService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;

    Logger logger = LoggerFactory.getLogger(UserUserAuthServiceImpl.class);

    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        logger.info("start service..........................");
        String phoneNumber = registerDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            logger.error("DuplicatedException: phone number already exists ");
            throw new DuplicatedException("phone number already exists");
        }
        User newUser = User.builder()
                .phoneNumber(registerDTO.getPhoneNumber())
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
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
        Role role = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Cannot find Role witch id: " + 2));
        newUser.setRole(role);
        logger.info("Stop service..........................");
        return userRepository.save(newUser);
    }


    @Override
    public User login(LoginDTO loginDTO) throws Exception {
        return null;
    }

}
