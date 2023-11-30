package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.PhoneNumberDTO;
import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.responses.UserResponses;

import java.util.List;

public interface IUserService {

    User login(LoginDTO loginDTO) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;

    List<User> families(String phoneNumber) throws Exception;

    UserResponses getUserByPhoneNumber(PhoneNumberDTO phoneNumberDTO) throws Exception;

    User updateSecurityCode(SecurityDTO securityDTO) throws Exception;

    boolean getSecurityCodeByPhoneNumber(SecurityDTO phoneNumberDTO) throws  Exception;

    User updateUser(UserDTO userDTO) throws Exception;
}
