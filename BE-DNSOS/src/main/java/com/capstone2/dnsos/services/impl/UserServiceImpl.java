package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.dto.FamilyDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RegisterDTO;
import com.capstone2.dnsos.exceptions.DataNotFoundException;
import com.capstone2.dnsos.models.Role;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.RoleRepository;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.responses.FamiliesResponses;
import com.capstone2.dnsos.responses.FamilyResponses;
import com.capstone2.dnsos.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User login(LoginDTO loginDTO) throws Exception {
        return null;
    }

    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        String phoneNumber = registerDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataNotFoundException("phone number already exists");
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
        Optional<User> familyId = userRepository.findByPhoneNumber(registerDTO.getPhoneFamily());
        // check family
        familyId.ifPresent(user -> newUser.setFamilyId(user.getFamilyId()));
        // set role
        Role role = roleRepository.findById(1L);
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    @Override
    public FamiliesResponses families(FamilyDTO  familyDTO) throws Exception {
        String phoneNumber = familyDTO.getPhoneNumber();
        User exstingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("cannot find family not found phone number: " + phoneNumber));
        List<User> users = userRepository.findByFamilyId(exstingUser.getFamilyId());
        return FamiliesResponses.mapperListUser(users);
    }
}
