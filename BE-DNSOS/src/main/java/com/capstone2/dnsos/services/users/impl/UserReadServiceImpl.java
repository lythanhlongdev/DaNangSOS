package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.*;
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
    public AvatarResponse getAvatar() throws Exception {
        return AvatarResponse.builder()
                .avatarName(currenUser().getAvatar())
                .userId(currenUser().getId())
                .build();
    }

    @Override
    public UserNotPasswordResponses getUserByPhoneNumber() throws Exception {
        User loadUser = this.currenUser();
        User existingUser = userRepository.findByPhoneNumber(loadUser.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + loadUser.getPhoneNumber()));
        List<User> families = userRepository.findByFamilyId(existingUser.getFamily().getId());
        return UserNotPasswordResponses.mapper(existingUser, families);
    }

    @Override
    public Page<PageUserResponse> getAllUser(PageRequest pageRequest) throws Exception {
        return userRepository.findAll(pageRequest).map(PageUserResponse::mapper);
    }

    @Override
    public DetailUserResponse getDetailUserById(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng có id: " + userId));
        List<User> families = userRepository.findByFamily(user.getFamily());
        return DetailUserResponse.mapper(user,families);
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
