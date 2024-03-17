package com.capstone2.dnsos.repositories.main;


import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.models.main.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("MainDatabaseConfig")
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    void  deleteByUserId(Long userId);
//    Token findByRefreshToken(String token);
}

