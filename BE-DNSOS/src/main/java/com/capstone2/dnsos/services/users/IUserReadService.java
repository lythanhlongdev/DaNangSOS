package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.responses.FamilyResponses;
import com.capstone2.dnsos.responses.UserResponses;

import java.util.List;

public interface IUserReadService {
    List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception;

    UserResponses getUserByPhoneNumber(String phoneNumber) throws Exception;

    boolean getSecurityCodeByPhoneNumber(SecurityDTO phoneNumberDTO) throws Exception;
}
