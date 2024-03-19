package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.UserNotPasswordResponses;
import com.capstone2.dnsos.responses.main.UserResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserReadService {
    List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception;
    Page<UserResponses> getAllUser(PageRequest pageRequest) throws Exception;
    UserNotPasswordResponses getUserByPhoneNumber() throws Exception;

//    boolean getSecurityCodeByPhoneNumber(SecurityDTO securityDTO) throws Exception;
}
