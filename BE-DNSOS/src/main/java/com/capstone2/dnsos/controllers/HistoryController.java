package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.HistoryMediaDTO;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.services.impl.HistoryServiceImpl;
import com.capstone2.dnsos.utils.FileUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/histories")
public class HistoryController {

    private final HistoryServiceImpl historyService;

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

    @PostMapping(value = "/uploads/{historyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMedia(@Valid @PathVariable("historyId") Long historyId, @ModelAttribute List<MultipartFile> files) {
        try {

            History existingHistory = historyService.getHistoryById(historyId);
            List<HistoryMediaDTO> listMedia = new ArrayList<>();
            String[] fileName = {"", "", "", ""};
            int i = 0;
            // neu co anh moi luua
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    // kiểm tra tệp img, nếu img > 10 MB
                    if (file.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File too large! Max size is 10MB");
                    }

                    // Kiểm tra loại tệp, phải là hình ảnh hoặc âm thanh MP3
                    String contentType = file.getContentType().toLowerCase();
                    if ((!contentType.startsWith("image/") && (!contentType.startsWith("audio/") ))){
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image or an audio file (MP3)");
                    }
                    // lưu file
                    fileName[i++] = FileUtil.saveImgAndMp3(file);
                }
                existingHistory.setImage1(fileName[0]);
                existingHistory.setImage2(fileName[1]);
                existingHistory.setImage3(fileName[2]);
                existingHistory.setVoice(fileName[3]);
                historyService.uploadMediaHistory(existingHistory);
            }else {
                return ResponseEntity.ok("History does not update media because not have a file");
            }
            return ResponseEntity.ok(listMedia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


//    @PostMapping(value = "/uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadImgs(@Valid @PathVariable("productId") Long productId, @ModelAttribute List<MultipartFile> files) {
//        try {
//            // user no upload file files.size = 1 =>  null, check
//            List<ProductImage> listImg = new ArrayList<>();
//            Product existingProduct = productService.getProductById(productId);
//            // neu co anh moi luua
//            if (files != null && files.size() != 0) {
//                for (MultipartFile file : files) {
//                    // kiểm tra tệp img, nếu img > 10 MB
//                    if (file.getSize() > 10 * 1024 * 1024) {
//                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File to large ! max size is 10MB");
//                    }
//                    // kiểm tra loại tệp, phải là hình ảnh
//                    String contentType = file.getContentType();
//                    if (contentType == null || contentType.startsWith("img/")) {
//                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image" + contentType);
//                    }
//                    // lưu tệp hoặc thay đổi tệp
//                    String fileName = FileUtil.saveImg(file);
//
//                    // mapping url and save img
//                    ProductImageDTO productImageDTO = ProductImageDTO.builder().productId(existingProduct.getId()).imgUrl(fileName).build();
//                    ProductImage productImg = productService.ceateProductImage(existingProduct.getId(), productImageDTO);
//                    listImg.add(productImg);
//                }
//            }
//            return ResponseEntity.ok(listImg);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }