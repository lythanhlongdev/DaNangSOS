package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryLog;
import com.capstone2.dnsos.models.main.Report;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.histories.*;
import com.capstone2.dnsos.services.reports.IReportService;
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

    // kiểm t ra lại nếu chưa dừng lại không cho tạo
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createHistory(@Valid @RequestBody HistoryDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            HistoryUserResponses history = historyCreateService.createHistory(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Create History successfully", 200, history));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/gps")
    public ResponseEntity<?> updateHistoryGPS(@Valid @RequestBody GpsDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            boolean isCheck = updateHistoryService.updateHistoryGPS(request);
            if (!isCheck) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Update False ", 400, ""));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/user/status")
    public ResponseEntity<?> updateHistoryCancelUser(@Valid @RequestBody CancelDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatusCancelUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
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
    public ResponseEntity<?> updateStatusHistory(@Valid @RequestBody StatusDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            boolean isCheck = updateHistoryService.updateHistoryStatus(request);
            if (!isCheck) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Update False", 400, ""));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, isCheck));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    // sos ,  upload
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/{history_id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(@Valid @PathVariable("history_id") Long historyId, @ModelAttribute List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("File empty", 200, ""));
            }
            return ResponseEntity.ok(historyMediaService.uploadHistoryMedia(historyId, files));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    //    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/media/{media_name}")
    public ResponseEntity<?> viewImage(@PathVariable("media_name") String mediaName) {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsesEntity(e.getMessage(), HttpStatus.NOT_FOUND.value(), ""));
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
    public ResponseEntity<?> getAllHistoryByUser() {
        try {
            List<ListHistoryByUserResponses> ls = historyReadService.getAllHistoryByUser();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @GetMapping("/all/rescue_station")
    public ResponseEntity<?> getAllHistoryByRescueStation() {
        try {
            List<ListHistoryByRescueStationResponses> ls = historyReadService.getAllHistoryByRescueStation();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
    @GetMapping("/rescue_station")
    public ResponseEntity<?> getAllHistoryNotConfirmedAndCancelByRescueStation() {
        try {
            List<ListHistoryByRescueStationResponses> ls = historyReadService.getAllHistoryNotConfirmedAndCancelByRescueStation();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE','USER')")
    @PostMapping("/report")
    public ResponseEntity<?> createReport(@Valid @RequestBody ReportDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            Report report = reportService.createReport(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Create successfully", 200, report));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE','USER')")
    @GetMapping("/{history_id}/report")
    public ResponseEntity<?> getAllReportByHistoryId(@Valid @PathVariable("history_id") Long historyId) {
        try {
            List<Report> reports = reportService.readReports(historyId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get successfully", 200, reports));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @GetMapping("/{history_id}/log")
    public ResponseEntity<?> getLogByHistoryId(@Valid @PathVariable("history_id") Long historyId) {
        try {
            List<HistoryLogResponses> logs = logService.readLogByHistoryId(historyId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get successfully", 200, logs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

}

