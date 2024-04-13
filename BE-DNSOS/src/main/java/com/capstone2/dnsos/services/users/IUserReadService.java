package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.UserResponses;

import java.util.List;

public interface IUserReadService {
    List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception;

    UserResponses getUserByPhoneNumber(String phoneNumber) throws Exception;

    boolean getSecurityCodeByPhoneNumber(SecurityDTO securityDTO) throws Exception;
}
