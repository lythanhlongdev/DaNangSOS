package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import com.capstone2.dnsos.responses.main.UserForAdminResponses;
import com.capstone2.dnsos.services.users.IUserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReadServiceImpl implements IUserReadService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;


    private User currenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user witch phone number: " + phoneNumber));
        List<User> users = userRepository.findByFamilyId(existingUser.getFamily().getId());
        return users.stream().map(FamilyResponses::mapperUser).toList();
    }

//      v 1.1.0
//    @Override
//    public UserNotPasswordResponses getUserByPhoneNumber() throws Exception {
//        User loadUser = this.currenUser();
//        User existingUser = userRepository.findByPhoneNumber(loadUser.getPhoneNumber())
//                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + loadUser.getPhoneNumber()));
//        return UserNotPasswordResponses.mapper(existingUser);
//    }

    @Override
    public UserNotPasswordResponses getUserByPhoneNumber() throws Exception {
        User loadUser = this.currenUser();
        User existingUser = userRepository.findByPhoneNumber(loadUser.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + loadUser.getPhoneNumber()));
        List<User> families = userRepository.findByFamilyId(existingUser.getFamily().getId());
        return UserNotPasswordResponses.mapper(existingUser, families);
    }

    @Override
    public Page<UserForAdminResponses> getAllUser(PageRequest pageRequest) throws Exception {
        return userRepository.findAll(pageRequest).map(UserForAdminResponses::mapper);
    }

    //    @Override
//    public boolean getSecurityCodeByPhoneNumber(SecurityDTO securityDTO) throws Exception {
//        String phoneNumber = securityDTO.getPhoneNumber();
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
//        if (existingUser.getSecurityCode() == null) {
//            throw new NullPointerException("Security code is null witch phone number: " + phoneNumber);
//        }
//        long code = Long.parseLong(securityDTO.getSecurityCode());
//        return existingUser.getSecurityCode() == code;
//    }


}
