package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.HistoryUserResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByUserResponses;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.histories.IHistoryCreateDeleteService;
import com.capstone2.dnsos.services.histories.IHistoryMediaService;
import com.capstone2.dnsos.services.histories.IHistoryReadService;
import com.capstone2.dnsos.services.histories.IHistoryUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/histories")
public class HistoryController {


    private final IHistoryCreateDeleteService historyCreateService;
    private final IHistoryReadService historyReadService;
    private final IHistoryUpdateService updateHistoryService;
    private final IHistoryMediaService historyMediaService;

    @PostMapping("")
    public ResponseEntity<?> createHistory(@Valid @RequestBody HistoryDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            HistoryUserResponses history = historyCreateService.createHistory(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Create History successfully",200,history));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Create History failed",400,""));
        }
    }

    @PutMapping("/gps")
    public ResponseEntity<?> updateHistoryGPS(@Valid @RequestBody GpsDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            History isCheck = updateHistoryService.updateHistoryGPS(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Update History GPS failed",400,""));
        }
    }


    @PutMapping("/confirmed")
    public ResponseEntity<?> updateStatusConfirmed(@Valid @RequestBody ConfirmedDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatusConfirmed(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @PutMapping("/user/cancel")
    public ResponseEntity<?> updateHistoryCancel(@Valid @RequestBody CancelDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatusCancelUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }
    @PutMapping("/rescue_station/cancel")
    public ResponseEntity<?> updateHistoryCancelUser(@Valid @RequestBody CancelDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatusCancel(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatusHistoryById(@Valid @RequestBody StatusDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatus(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    // sos ,  upload
    @PutMapping(value = "/media/{historyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(@Valid @PathVariable("historyId") Long historyId, @ModelAttribute List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("File empty", 200, ""));
            }
            return ResponseEntity.ok(historyMediaService.uploadHistoryMedia(historyId, files));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }


    // Page and limit
    @GetMapping("/user/{phoneNumber}")
    public ResponseEntity<?> getAllHistoryByUser(@Valid @PathVariable("phoneNumber") String phoneNumber) {
        try {
            List<ListHistoryByUserResponses> ls = historyReadService.getAllHistoryByUser(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @GetMapping("/all/rescue_station/{phoneNumber}")
    public ResponseEntity<?> getAllHistoryByRescueStation(@Valid @PathVariable("phoneNumber") String phoneNumber) {
        try {
            List<ListHistoryByRescueStationResponses> ls = historyReadService.getAllHistoryByRescueStation(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @GetMapping("/rescue_station/{phoneNumber}")
    public ResponseEntity<?> getAllHistoryNotConfirmedAndCancelByRescueStation(@Valid @PathVariable("phoneNumber") String phoneNumber) {
        try {
            List<ListHistoryByRescueStationResponses> ls = historyReadService.getAllHistoryNotConfirmedAndCancelByRescueStation(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

}

