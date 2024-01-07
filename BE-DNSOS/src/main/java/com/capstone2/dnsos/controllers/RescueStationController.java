package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/rescue_stations")
public class RescueStationController {

    private final IRescueStationAuthService rescueStationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RescueStationDTO request, BindingResult error){
        try {
            if (error.hasErrors()){
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            // check match password
            if (!request.getPassword().equals(request.getRetypePassword())) { 
                throw new NotFoundException("Password not match");
            }
            RescueStation newR = rescueStationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Register new Rescue Station successfully",200,newR));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }
}
