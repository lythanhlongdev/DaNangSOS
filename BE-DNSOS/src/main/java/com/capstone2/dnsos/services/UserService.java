package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.FamilyDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RegisterDTO;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.responses.FamiliesResponses;
import com.capstone2.dnsos.responses.FamilyResponses;

import java.util.List;

public interface UserService {

    User login(LoginDTO loginDTO) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;

    FamiliesResponses families(FamilyDTO familyDTO) throws Exception;

}
