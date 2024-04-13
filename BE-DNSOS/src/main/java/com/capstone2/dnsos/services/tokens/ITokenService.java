package com.capstone2.dnsos.services.tokens;

import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.models.main.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
