package com.capstone2.dnsos.services.users.impl;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.BadCredentialsException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Family;
import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.repositories.main.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserUserAuthServiceImpl implements IUserAuthService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private  final TokenRepository tokenRepository;

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
        // Encode password
        String enCodePassword = passwordEncoder.encode(registerDTO.getPassword());
        newUser.setPassword(enCodePassword);
        logger.info("Stop service..........................");
        return userRepository.save(newUser);
    }


    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        User exsitingUser = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber())
                .orElseThrow(()-> new NotFoundException("Invalid phone number/password"));
        // check pass
        if (!passwordEncoder.matches(loginDTO.getPassword(), exsitingUser.getPassword())){
            throw  new BadCredentialsException("Wrong phone number or password ");
        }
        // check auth
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
               loginDTO.getPhoneNumber(),loginDTO.getPassword(),exsitingUser.getAuthorities()
       );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(exsitingUser);
    }


    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtils.isTokenExpired(token)) {
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
    @Override
    public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        return getUserDetailsFromToken(existingToken.getToken());
    }
}
