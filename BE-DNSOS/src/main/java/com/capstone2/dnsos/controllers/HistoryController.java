package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import com.capstone2.dnsos.responses.ResponsesEntity;
import com.capstone2.dnsos.services.IHistoryMediaService;
import com.capstone2.dnsos.services.IHistoryService;
import com.capstone2.dnsos.services.impl.HistoryServiceImpl;

import com.capstone2.dnsos.utils.FileUtil;
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

    private final IHistoryService historyService;
    private final IHistoryMediaService historyMediaService;

    @PostMapping("/sos")
    public ResponseEntity<?> createHistory(@Valid @RequestBody HistoryDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(listError);
            }
            return ResponseEntity.ok(historyService.createHistory(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateStatusHistoryById(@Valid @RequestBody StatusDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(listError);
            }
            boolean isCheck = historyService.updateStatusHistory(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // sos ,  upload
    @PutMapping(value = "/uploads/media/{historyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(@Valid @PathVariable("historyId") Long historyId, @ModelAttribute List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("File empty", 200, ""));
            }
            return ResponseEntity.ok(historyMediaService.uploadHistoryMedia(historyId, files));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // test
    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable("id") Long historyId) {
        try {
            History history = historyService.getHistoryById(historyId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Page and limit
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllHistoryByUser(@Valid @PathVariable("id") long userId) {
        try {
            List<ListHistoryByUserResponses> ls = historyService.getAllHistoryByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //test
    @GetMapping("")
    public ResponseEntity<?> getAllHistory() {
        try {
            List<History> histories = historyService.getAllHistory();
            return ResponseEntity.ok(histories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

