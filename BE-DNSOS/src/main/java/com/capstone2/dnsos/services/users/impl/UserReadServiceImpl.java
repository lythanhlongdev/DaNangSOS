package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.AvatarResponse;
import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import com.capstone2.dnsos.responses.main.UserForAdminResponses;
import com.capstone2.dnsos.services.address.impl.AddressImpl;
import com.capstone2.dnsos.services.users.IUserReadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserReadServiceImpl.class);

    private User currenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception {
        if (phoneNumber.isEmpty())
        {
            logger.info("getAllUserByFamily(), The value of phoneNumber is empty");
            return null;
        }
        try
        {
            User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new NotFoundException("Cannot find user witch phone number: " + phoneNumber));
            List<User> users = userRepository.findByFamilyId(existingUser.getFamily().getId());
            return users.stream().map(FamilyResponses::mapperUser).toList();
        }
        catch (Exception e)
        {
            logger.error("Error retrieving users with phone number {}: {}", phoneNumber, e.getMessage(), e);
            throw new NotFoundException("Error retrieving users with phone number: " + phoneNumber + e);
        }
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
        try
        {
            User existingUser = userRepository.findByPhoneNumber(loadUser.getPhoneNumber())
                    .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + loadUser.getPhoneNumber()));
            List<User> families = userRepository.findByFamilyId(existingUser.getFamily().getId());
            return UserNotPasswordResponses.mapper(existingUser, families);
        }
        catch (Exception e)
        {
            logger.error("getUserByPhoneNumber(), Error retrieving users with phone number "+loadUser.getPhoneNumber());
            throw new NotFoundException("Error retrieving users with phone number "+ loadUser.getPhoneNumber());
        }

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
