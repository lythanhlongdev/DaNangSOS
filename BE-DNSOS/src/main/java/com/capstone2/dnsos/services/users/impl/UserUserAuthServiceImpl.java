package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import org.springframework.security.authentication.AuthenticationManager;
import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IFamilyRepository;
import com.capstone2.dnsos.repositories.main.IRoleRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.services.users.IUserAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserUserAuthServiceImpl implements IUserAuthService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(UserUserAuthServiceImpl.class);

    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        logger.info("start service..........................");
        String phoneNumber = registerDTO.getPhoneNumber();

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            logger.error("DuplicatedException: phone number already exists ");
            throw new DuplicatedException("phone number already exists");
        }
        User newUser = Mappers.getMappers().mapperUser(registerDTO);

        String phoneFamily = registerDTO.getPhoneFamily();

        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(phoneFamily)
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + phoneFamily));
        newUser.setFamily(family);
        // set role
        Role role = roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find Role witch id: " + 4));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
        // Encode password
        String enCodePassword = passwordEncoder.encode(registerDTO.getPassword());
        newUser.setPassword(enCodePassword);
        logger.info("Stop service..........................");
        return userRepository.save(newUser);
    }


    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        User exsitingUser = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Invalid phone number/password"));
        // check pass
        if (!passwordEncoder.matches(loginDTO.getPassword(), exsitingUser.getPassword())) {
            throw new BadCredentialsException("Wrong phone number or password ");
        }
        // check auth
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getPhoneNumber(), loginDTO.getPassword(), exsitingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(exsitingUser);
    }

    @Override
    public void lockUser(String phoneNumber) throws Exception {
        User exitingUser = this.getUser(phoneNumber);
        if (this.currenUser().getPhoneNumber().equals(exitingUser.getPhoneNumber())) {
            throw new InvalidParameterException("You cannot lock your account yourself because this is an admin account");
        }
        if (!exitingUser.getIsActivity()) return;
        // lock user and lock rescue
        if (exitingUser.getRescueStation() != null) {
            exitingUser.getRescueStation().setIsActivity(false);
        }
        exitingUser.setIsActivity(false);
        userRepository.save(exitingUser);
    }

    // trương hợp phiên cứu hộ chưa hoàn thành mà mình khóa tài khoản chưa bắt key này 
    @Override
    public void unLockUser(String phoneNumber) throws Exception {
        User exitingUser = this.getUser(phoneNumber);
        if (this.currenUser().getPhoneNumber().equals(exitingUser.getPhoneNumber())) {
            throw new InvalidParameterException("You cannot Unlock your account yourself because this is an admin account");
        }
        if (exitingUser.getIsActivity()) return;
        if (exitingUser.getRescueStation() != null) {
            exitingUser.getRescueStation().setIsActivity(true);
        }
        exitingUser.setIsActivity(true);
        userRepository.save(exitingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtils.extractPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }


    private User getUser(String phoneNumber) throws Exception {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("Cannot find User with phone number: " + phoneNumber));
    }

    private User currenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
//    @Override
//    public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
//        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
//        return getUserDetailsFromToken(existingToken.getToken());
//    }
}
