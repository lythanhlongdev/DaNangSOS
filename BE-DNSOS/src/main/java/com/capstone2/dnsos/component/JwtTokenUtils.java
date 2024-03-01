package com.capstone2.dnsos.component;

import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.secretKey}")
    public String secretKey;

    private  final  TokenRepository tokenRepository;
    private  static  final  Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    public  String generateToken(com.capstone2.dnsos.models.main.User user) throws Exception{
        LOGGER.info("Create Token");
        Map<String,Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber",user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration *1000L))
                    .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            LOGGER.info("Create Token False {}",e.getMessage());
            throw  new Exception(e.getMessage());
        }
    }

    public  String generateTokenForRescue(com.capstone2.dnsos.models.main.RescueStation rescue) throws Exception{
        LOGGER.info("Create Token");
        Map<String,Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber",rescue.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(rescue.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration *1000L))
                    .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            LOGGER.info("Create Token False {}",e.getMessage());
            throw  new Exception(e.getMessage());
        }
    }
//
//    @Value("${jwt.expiration-refresh-token}")
//    private int expirationRefreshToken;
//
//    @Value("${jwt.secretKey}")
//    private String secretKey;
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
//    private final TokenRepository tokenRepository;
//    public String generateToken(com.capstone2.dnsos.models.main.User user) throws Exception{
//        //properties => claims
//        Map<String, Object> claims = new HashMap<>();
//        //this.generateSecretKey();
//        claims.put("phoneNumber", user.getPhoneNumber());
//        claims.put("userId", user.getUserId());
//        try {
//            String token = Jwts.builder()
//                    .setClaims(claims) //how to extract claims from this ?
//                    .setSubject(user.getPhoneNumber())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
//                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                    .compact();
//            return token;
//        }catch (Exception e) {
//            //you can "inject" Logger, instead System.out.println
//            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
//            //return null;
//        }
//    }
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey); // Decoders.BASE64.decode("h38+qax6e3ZNYFc5yHjqpwvJidmJUOGdnUUDLok+zVg=")
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("h38+qax6e3ZNYFc5yHjqpwvJidmJUOGdnUUDLok+zVg="));
        return Keys.hmacShaKeyFor(bytes);
    }
//    private String generateSecretKey() {
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32]; // 256-bit key
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey ;
//    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, User userDetails) {
        try {
            String phoneNumber = extractPhoneNumber(token);
            Token existingToken = tokenRepository.findByToken(token);
            if(existingToken == null ||
                    existingToken.isRevoked() == true ||
                    !userDetails.isEnabled()
            ) {
                return false;
            }
            return (phoneNumber.equals(userDetails.getUsername()))
                    && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public boolean validateTokenForRescue(String token, User userDetails) {
        try {
            String phoneNumber = extractPhoneNumber(token);
            Token existingToken = tokenRepository.findByToken(token);
            if(existingToken == null ||
                    existingToken.isRevoked() == true ||
                    !userDetails.isEnabled()
            ) {
                return false;
            }
            return (phoneNumber.equals(userDetails.getUsername()))
                    && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
