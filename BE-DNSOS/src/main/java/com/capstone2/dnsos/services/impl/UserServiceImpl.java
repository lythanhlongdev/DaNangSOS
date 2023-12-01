package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.PhoneNumberDTO;
import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.models.Role;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.RoleRepository;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.responses.UserResponses;
import com.capstone2.dnsos.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final ModelMapper modelMapper;

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
        // this is bug
        Optional<User> familyId = userRepository.findByPhoneNumber(registerDTO.getPhoneFamily());// chỗ này có vấn đề
        // check family
        familyId.ifPresent(user -> newUser.setFamilyId(user.getFamilyId()));
        // set role
        Role role = roleRepository.findById(2L);
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    @Override
    public List<User> families(String phoneNumber) throws Exception {
        // check phone
        User exstingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find family with phone number: " + phoneNumber));
        // get family id
        long familyId = exstingUser.getFamilyId();
        return userRepository.findByFamilyId(familyId);
    }

    @Override
    public UserResponses getUserByPhoneNumber(PhoneNumberDTO phoneNumberDTO) throws Exception {
        String phoneNumber = phoneNumberDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        long familyId = existingUser.getFamilyId();// bug null if family id null
        List<User> families = userRepository.findByFamilyId(familyId);
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
    public User updateUser(UserDTO userDTO) throws Exception {
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
                .roleFamily(userDTO.getRoleFamily())
                .build();
        return null;
    }
}
