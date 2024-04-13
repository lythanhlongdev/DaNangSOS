package com.capstone2.dnsos.repositories.main;


import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.models.main.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}

