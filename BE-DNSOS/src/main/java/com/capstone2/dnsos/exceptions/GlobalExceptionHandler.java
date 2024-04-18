package com.capstone2.dnsos.exceptions;

import com.capstone2.dnsos.responses.main.ResponsesEntity;
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
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponsesEntity> handlerException(Exception exception) {
        return ResponseEntity.internalServerError().body(
                new ResponsesEntity(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        ""));
    }
}
