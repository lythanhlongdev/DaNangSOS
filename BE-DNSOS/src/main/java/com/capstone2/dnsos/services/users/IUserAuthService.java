package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.User;

public interface IUserAuthService {
    User login(LoginDTO loginDTO) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;
}
