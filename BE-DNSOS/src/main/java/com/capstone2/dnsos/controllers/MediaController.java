package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.responses.main.HistoryMediaResponses;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.histories.IHistoryMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/medias")
public class MediaController {
    private final IHistoryMediaService historyMediaService;
    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @PreAuthorize("hasAnyRole('ROLE_USER')")
//        @PostMapping(value = "/history_id", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/{history_id}")
    public ResponseEntity<?> uploadMediaHistory(
            @Valid @PathVariable("history_id") Long historyId,
            @RequestParam("img1") MultipartFile img1,
            @RequestParam("img2") MultipartFile img2,
            @RequestParam("img3") MultipartFile img3,
            @RequestParam("voice") MultipartFile voice) {
        try {
            logger.info("Bắt đầu controller uploadMediaHistory:..................................");
            HistoryMediaResponses historyMediaResponses = historyMediaService.uploadHistoryMedia(historyId, img1, img2, img3, voice);
            logger.info("Kết thúc controller uploadMediaHistory:..................................");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Cập nhật thành công media", 200, historyMediaResponses));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }
}

