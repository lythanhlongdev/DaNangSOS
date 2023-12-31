package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
                return ResponseEntity.badRequest().body(listError);
            }
            // check match password
            if (!request.getPassword().equals(request.getRetypePassword())) { 
                throw new NotFoundException("Password not match");
            }
            RescueStation newR = rescueStationService.register(request);

            return ResponseEntity.ok().body(newR);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
