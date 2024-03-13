package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.UserResponses;

public interface IUserUpdateDeleteService {

    UserResponses updateUser(UserDTO userDTO) throws Exception;

//    User updateSecurityCode(SecurityDTO securityDTO) throws Exception;

    boolean deleteUser(String userId) throws Exception;
}
