package com.capstone2.dnsos.exceptions.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public class GetErorrInRequest {

    public static ResponseEntity errMessage(BindingResult result){
        if (result.hasErrors() == true) {
            List<String> errMessage = result.getAllErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errMessage);
        }
        return null;
    }
}
