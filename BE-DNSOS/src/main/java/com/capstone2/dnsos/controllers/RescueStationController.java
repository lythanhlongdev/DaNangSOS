package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.responses.main.RescueStationResponses2;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/rescue_stations")
public class RescueStationController {

    private final IRescueStationAuthService rescueStationService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RescueStationDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            if (!request.getPassword().equals(request.getRetypePassword())) {
                throw new InvalidParamException("Password not match");
            }
            RescueStationResponses2 newR = rescueStationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Register new Rescue Station successfully", 200, newR));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasRole('ROLE_RESCUE')")
    @GetMapping()
    public ResponseEntity<?> getInfoRescue() {
        try {
            RescueStationResponses2 rescue = rescueStationService.getInfoRescue();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get Rescue Station successfully", 200, rescue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasRole('ROLE_RESCUE')")
    @PutMapping()
    public ResponseEntity<?> changeInfoRescue(@Valid @RequestBody UpdateRescueDTO updateRescueDTO, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            RescueStationResponses2 rescue = rescueStationService.UpdateInfoRescue(updateRescueDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get Rescue Station successfully", 200, rescue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }
}
