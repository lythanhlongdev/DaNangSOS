package com.capstone2.dnsos.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/histories")
public class HistoryController {


    @PutMapping()
    public ResponseEntity<?> createHistory(){
        return null;
    }
}
