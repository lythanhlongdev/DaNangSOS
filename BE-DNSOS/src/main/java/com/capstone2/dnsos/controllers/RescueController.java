package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import com.capstone2.dnsos.responses.main.RescueResponse;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.rescue.IRescueService;
import com.capstone2.dnsos.services.rescue.impl.RescueService;
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
@RequestMapping("${api.prefix}/rescue")
public class RescueController {

    private final IRescueService rescueService;


    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponsesEntity.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(listError.toString())
                            .build());
        }

        RescueResponse rescueResponse = rescueService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Register Rescue successfully", HttpStatus.CREATED.value(), rescueResponse));
    }


    @PreAuthorize("hasAnyRole('ROLE_RESCUE_WORKER')")
    @PostMapping("/scan_qr")
    public ResponseEntity<?> scanQrCode(@Valid @RequestBody GpsDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponsesEntity.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(listError.toString())
                            .build());
        }

        RescueByHistoryResponse rescueResponse = rescueService.scanQrCode(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get current user information successfully", HttpStatus.OK.value(), rescueResponse));
    }
}
