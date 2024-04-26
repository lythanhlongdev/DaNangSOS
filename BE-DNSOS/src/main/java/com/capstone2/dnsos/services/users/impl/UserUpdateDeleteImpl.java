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
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import com.capstone2.dnsos.responses.main.UserResponse;
import com.capstone2.dnsos.services.users.IUserUpdateDeleteService;
import com.capstone2.dnsos.utils.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserUpdateDeleteImpl implements IUserUpdateDeleteService {

    private final IUserRepository userRepository;
    private final IFamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtils;

    private User getCurrenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserNotPasswordResponses updateUser(UserDTO userDTO) throws Exception {
        String phoneNumber = this.getCurrenUser().getPhoneNumber();
        String password = userDTO.getPassword();

        if (!passwordEncoder.matches(password, this.getCurrenUser().getPassword())) {
            throw new InvalidParamException("Invalid password");
        }
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
        Family family = userDTO.getFamilyPhoneNumber().isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(userDTO.getFamilyPhoneNumber())
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + userDTO.getFamilyPhoneNumber()));
        existingUser.setFamily(family);
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setBirthday(userDTO.getBirthday());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setRoleFamily(userDTO.getRoleFamily());
        User updateUser = userRepository.save(existingUser);
        List<User> families = userRepository.findByFamilyId(updateUser.getFamily().getId());
        return UserNotPasswordResponses.mapper(updateUser, families);
    }

//    @Override
//    public UserNotPasswordResponses updateUser(UserDTO userDTO) throws Exception {
//        String phoneNumber = this.getCurrenUser().getPhoneNumber();
//        String password = userDTO.getPassword();
//
//        if (!passwordEncoder.matches(password, this.getCurrenUser().getPassword())) {
//            throw new InvalidParamException("Invalid password");
//        }
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
//        Family family = userDTO.getFamilyPhoneNumber().isEmpty() ? familyRepository.save(new Family()) :
//                userRepository.findByPhoneNumber(userDTO.getFamilyPhoneNumber())
//                        .map(User::getFamily)
//                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + userDTO.getFamilyPhoneNumber()));
//        existingUser.setFamily(family);
//        existingUser.setFirstName(userDTO.getFirstName());
//        existingUser.setLastName(userDTO.getLastName());
//        existingUser.setBirthday(userDTO.getBirthday());
//        existingUser.setAddress(userDTO.getAddress());
//        existingUser.setRoleFamily(userDTO.getRoleFamily());
//        User updateUser = userRepository.save(existingUser);
//        return UserNotPasswordResponsesv_1_1_0.mapper(updateUser);
//    }

//    @Override
//    public User updateSecurityCode(SecurityDTO securityDTO) throws Exception {
//        String phoneNumber = securityDTO.getPhoneNumber();
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new NotFoundException("cannot find user with phone number: " + phoneNumber));
//        long securityCode = Long.parseLong(securityDTO.getSecurityCode());
//        existingUser.setSecurityCode(securityCode);
//        return userRepository.save(existingUser);
//    }


    @Override
    public UserResponse updateAvatar(MultipartFile avatar) throws Exception {
        User currentUser = getCurrenUser();
        currentUser.setAvatar(FileUtil.saveAvatar(avatar, currentUser.getId(),1));
        currentUser = userRepository.save(currentUser);
        return UserResponse.mapper(currentUser);
    }


    @Transactional
    @Override
    public String ChangePassword(PasswordDTO passwordDTO) throws Exception {
        // load user da xac nhan
        String phoneNumber = this.getCurrenUser().getPhoneNumber();
        User exsitingUser = this.getUser(phoneNumber);
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
    public String forgotPassword(String phoneNumber) throws Exception {

        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("phone number does not exist: " + phoneNumber));

        boolean isAdmin = existingUser.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            throw new Exception("You do not have permission to find the password with this phone number");
        }
        // create new password
        String newPassword = "User" + existingUser.getFirstName() + LocalDate.now().getDayOfMonth();
        // set and save password
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
        // delete all token for user
        tokenRepository.deleteByUserId(existingUser.getId());
        return newPassword;
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
