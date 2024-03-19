package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IUserAuthService {
    String login(LoginDTO loginDTO) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;

    void lockUser(String phoneNumber) throws Exception;

    void unLockUser(String phoneNumber) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;
//    User getUserDetailsFromRefreshToken(String token) throws Exception;
//    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;
}
