package com.capstone2.dnsos.exceptions;

import com.capstone2.dnsos.exceptions.exception.AccessDeniedException;
import com.capstone2.dnsos.exceptions.exception.AuthenticationException;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice // this is class Exception
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ResponsesEntity> handlerException(Exception exception) {
//        return ResponseEntity.internalServerError().body(
//                new ResponsesEntity(
//                        exception.getMessage(),
//                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        ""));
//    }\

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponsesEntity> handlerException(Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(
                new ResponsesEntity(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        ""));
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
    }
}
