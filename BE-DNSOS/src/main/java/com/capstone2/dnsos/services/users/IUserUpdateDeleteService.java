package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.PasswordDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;

public interface IUserUpdateDeleteService {

    UserNotPasswordResponses updateUser(UserDTO userDTO) throws Exception;

//    User updateSecurityCode(SecurityDTO securityDTO) throws Exception;

    boolean deleteUser(String userId) throws Exception;

    String ChangePassword(PasswordDTO passwordDTO) throws Exception;

    String forgotPassword(String phoneNumber) throws Exception;
}
