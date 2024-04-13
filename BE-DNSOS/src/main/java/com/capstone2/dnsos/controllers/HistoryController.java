package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Report;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.histories.*;
import com.capstone2.dnsos.services.reports.IReportService;

import com.capstone2.dnsos.models.main.History;

import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.repositories.main.ICancelHistoryRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;

import com.capstone2.dnsos.models.main.HistoryMedia;

import com.capstone2.dnsos.responses.main.HistoryUserResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByUserResponses;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.histories.IHistoryCreateDeleteService;
import com.capstone2.dnsos.services.histories.IHistoryMediaService;
import com.capstone2.dnsos.services.histories.IHistoryReadService;
import com.capstone2.dnsos.services.histories.IHistoryUpdateService;
import com.capstone2.dnsos.services.histories.impl.HistoryUpdateServiceIml;
import com.capstone2.dnsos.utils.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Paths;
import java.util.*;

import java.security.InvalidParameterException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/histories")
public class HistoryController {


    private final IHistoryCreateDeleteService historyCreateService;
    private final IHistoryReadService historyReadService;
    private final IHistoryUpdateService updateHistoryService;
    private final IHistoryMediaService historyMediaService;
    private final IReportService reportService;
    private final IHistoryChangeLogService logService;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryUpdateService historyUpdateService ;
    private final IHistoryRepository historyRepository;

    // kiểm t ra lại nếu chưa dừng lại không cho tạo
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createHistory(@Valid @RequestBody HistoryDTO request, BindingResult result) throws Exception {
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
        CreateHistoryByUserResponses createHistory = historyCreateService.createHistory(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Create History successfully", HttpStatus.CREATED.value(), createHistory));
    }

    private ResponseEntity<?> checkValidInDto(BindingResult result) {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoryById(@Valid @PathVariable("id") long id, BindingResult result) throws Exception {
        this.checkValidInDto(result);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Update status cancel successfully", HttpStatus.OK.value(), ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/gps")
    public ResponseEntity<?> updateHistoryGPS(@Valid @RequestBody GpsDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
        }
        updateHistoryService.updateHistoryGPS(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponsesEntity("Update GPS successfully", HttpStatus.CREATED.value(), ""));
    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/user/status")
    public ResponseEntity<?> updateHistoryCancelUser(@Valid @RequestBody CancelDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
        }
        updateHistoryService.updateHistoryStatusCancelUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Update status cancel successfully", HttpStatus.OK.value(), ""));
    }
//    @PutMapping("/rescue_station/cancel")
//    public ResponseEntity<?> updateHistoryStatusCancel(@Valid @RequestBody CancelDTO request, BindingResult result) {
//        try {
//            if (result.hasErrors()) {
//                List<String> listError = result.getAllErrors()
//                        .stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
//            }
//            boolean isCheck = updateHistoryService.updateHistoryStatusCancel(request);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
//        }
//    }


//    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
//    @PatchMapping("/status")
//    public ResponseEntity<?> updateStatusConfirmed(@Valid @RequestBody ConfirmedDTO request, BindingResult result) {
//        try {
//            if (result.hasErrors()) {
//                List<String> listError = result.getAllErrors()
//                        .stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
//            }
//            boolean isCheck = updateHistoryService.updateHistoryStatusConfirmed(request);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
//        }
//    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @PatchMapping("rescue_station/status")
    public ResponseEntity<?> updateStatusHistory(@Valid @RequestBody StatusDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), 400, ""));
        }
        Status status = updateHistoryService.updateHistoryStatus(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Update successfully", 200, status));
    }


    // sos ,  upload
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/{history_id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(
            @Valid @PathVariable("history_id") Long historyId,
            @ModelAttribute List<MultipartFile> files) throws Exception {
        if (files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("File empty", 200, ""));
        }
        return ResponseEntity.ok(historyMediaService.uploadHistoryMedia(historyId, files));
    }

    //    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/media/{media_name}")
    public ResponseEntity<?> viewImage(@PathVariable("media_name") String mediaName) throws Exception {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + mediaName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                MediaType mediaType = FileUtil.getMediaType(resource);

                return ResponseEntity.ok().contentType(mediaType).body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.NOT_FOUND.value(), ""));
        }
    }


//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @GetMapping("/{history_id}/media")
//    public ResponseEntity<?> viewImages(@PathVariable("history_id") long historyId) {
//        try {
//
//            HistoryMedia media = historyMediaService.getMediaByHistory(historyId);
//            Set<String> mediaNames = new HashSet<>();
//            mediaNames.add(media.getImage1());
//            mediaNames.add(media.getImage2());
//            mediaNames.add(media.getImage3());
//            mediaNames.add(media.getVoice());
//
//
//            Set<Resource> imageResources = new HashSet<>();
//
//            for (String name : mediaNames) {
//                java.nio.file.Path imagePath = Paths.get("./uploads/" + name);
//                Resource resource = new UrlResource(imagePath.toUri());
//
//                if (resource.exists() || resource.isReadable()) {
//                    imageResources.add(resource);
//                } else {
//                    imageResources.add(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
//                }
//            }
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResources.toString());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    // Page and limit
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllHistoryByUser() throws Exception {
        List<HistoryByUserResponses> listUserResponses = historyReadService.getAllHistoryByUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), listUserResponses));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @GetMapping("/all/rescue_station")
    public ResponseEntity<?> getAllHistoryByRescueStation() throws Exception {
        List<HistoryByRescueStationResponses> listHistoryByRescueStationResponses = historyReadService.getAllHistoryByRescueStation();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), listHistoryByRescueStationResponses));
    }

    // Đổi lai thanh socket
    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @GetMapping("/rescue_station")
    public ResponseEntity<?> getAllHistoryNotConfirmedAndCancel() throws Exception { // view map
        List<HistoryByRescueStationResponses> historiesNotConfirmedAndCancel = historyReadService.getAllHistoryNotConfirmedAndCancel();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), historiesNotConfirmedAndCancel));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE','USER')")
    @PostMapping("/report")
    public ResponseEntity<?> createReport(@Valid @RequestBody ReportDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), 400, ""));
        }
        Report report = reportService.createReport(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Create report successfully", 200, report));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE','USER')")
    @GetMapping("/{history_id}/report")
    public ResponseEntity<?> getAllReportByHistoryId(@Valid @PathVariable("history_id") Long historyId) {
        try {
            List<Report> reports = reportService.readReports(historyId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Get report successfully", 200, reports));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @GetMapping("/{history_id}/log")
    public ResponseEntity<?> getLogByHistoryId(@Valid @PathVariable("history_id") Long historyId) {
        try {
            List<HistoryLogResponses> logs = logService.readLogByHistoryId(historyId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Get log successfully", 200, logs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @GetMapping("/rescue_station/changeStation/{historyId}")
    public ResponseEntity<?> changeRescueStation(@Valid @PathVariable("historyId") Long historyId) throws Exception
    {
        if(!historyRepository.existsById(historyId))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsesEntity("History is not existing",400,""));
        }
        try
        {
            HistoryUserResponses result = historyUpdateService.changeRescueStation(historyId);
            if(result != null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Forwarding rescue station successfully", 200,result));
            }
        }
        catch(InvalidParameterException exe)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(exe.getMessage(),400,""));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Cannot forwarding rescue station",400,""));
    }

}

