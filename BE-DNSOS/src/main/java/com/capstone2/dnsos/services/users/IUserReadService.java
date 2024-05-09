package com.capstone2.dnsos.services.users;

import com.capstone2.dnsos.responses.main.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserReadService {
    List<FamilyResponses> getAllUserByFamily(String phoneNumber) throws Exception;

    Page<PageUserResponse> getAllUser(PageRequest pageRequest) throws Exception;
    DetailUserResponse getDetailUserById(Long userId)throws Exception;
    UserNotPasswordResponses getUserByPhoneNumber() throws Exception;

    AvatarResponse getAvatar() throws Exception;
//    boolean getSecurityCodeByPhoneNumber(SecurityDTO securityDTO) throws Exception;
}
