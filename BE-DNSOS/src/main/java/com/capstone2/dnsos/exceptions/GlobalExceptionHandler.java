package com.capstone2.dnsos.exceptions;

import com.capstone2.dnsos.exceptions.exception.ForbiddenException;
import com.capstone2.dnsos.exceptions.exception.UnauthorizedException;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponsesEntity> handleUnauthorizedException(UnauthorizedException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponsesEntity(
                        exception.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(),
                        "Unauthorized access"));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponsesEntity> handleForbiddenException(ForbiddenException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponsesEntity(
                        exception.getMessage(),
                        HttpStatus.FORBIDDEN.value(),
                        "Bạn không có quyền truy cập vào tài nguyên này"));
    }
}
