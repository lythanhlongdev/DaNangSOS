package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.dto.PasswordDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IFamilyRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.repositories.main.TokenRepository;
import com.capstone2.dnsos.responses.main.TokenAndNewPassword;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import com.capstone2.dnsos.services.users.IUserUpdateDeleteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserUpdateDeleteImpl implements IUserUpdateDeleteService {

    private final IUserRepository userRepository;
    private final IFamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserNotPasswordResponses updateUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        if (userDTO.getFamilyPhoneNumber().isEmpty()) {
            Family family = familyRepository.save(new Family());
            existingUser.setFamily(family);
        } else {
            User userCheck = userRepository.findByPhoneNumber(userDTO.getFamilyPhoneNumber()).orElse(null);
            if (userCheck != null) {
                Family family = userCheck.getFamily();
                existingUser.setFamily(family);
            }
        }
//        List<User> families = userRepository.findByFamily(existingUser.getFamily());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setBirthday(userDTO.getBirthday());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setRoleFamily(userDTO.getRoleFamily());
        User updateUser = userRepository.save(existingUser);
        List<User> families = userRepository.findByFamily(updateUser.getFamily());
        return UserNotPasswordResponses.mapper(updateUser, families);
    }

//    @Override
//    public User updateSecurityCode(SecurityDTO securityDTO) throws Exception {
//        String phoneNumber = securityDTO.getPhoneNumber();
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
//        long securityCode = Long.parseLong(securityDTO.getSecurityCode());
//        existingUser.setSecurityCode(securityCode);
//        return userRepository.save(existingUser);
//    }


    @Transactional
    @Override
    public String ChangePassword(PasswordDTO passwordDTO) throws Exception {
        // load user da xac nhan
        User loadUserByToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User exsitingUser = this.getUser(loadUserByToken.getPhoneNumber());
        // check password
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), exsitingUser.getPassword())) {
            throw new InvalidParamException("Invalid password");
        }
        // set and save new password
        exsitingUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(exsitingUser);
        // delete all token for user
        tokenRepository.deleteByUserId(exsitingUser.getId());

        return jwtTokenUtils.generateToken(exsitingUser);
    }

    @Transactional
    @Override
    public TokenAndNewPassword forgotPassword() throws Exception {
        // load user da xac nhan
        User loadUserByToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User exsitingUser = this.getUser(loadUserByToken.getPhoneNumber());
        // create new password
        String newPassword = "User" + exsitingUser.getFirstName() + LocalDate.now().getDayOfMonth();
        // set and save password
        exsitingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(exsitingUser);
        // delete all token for user
        tokenRepository.deleteByUserId(exsitingUser.getId());
        // new Token
        jwtTokenUtils.generateToken(exsitingUser);
        TokenAndNewPassword tokenAndNewPassword =  TokenAndNewPassword.builder()
                .token(jwtTokenUtils.generateToken(exsitingUser))
                .newPassword(newPassword)
                .build();
        return tokenAndNewPassword;
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return false;
    }


    private User getUser(String phoneNumber) throws Exception {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user with phone number: " + phoneNumber));
    }

}
