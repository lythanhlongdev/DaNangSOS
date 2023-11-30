package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.exceptions.HttpStatusExceptions;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import com.capstone2.dnsos.responses.ResponsesEntity;
import com.capstone2.dnsos.services.impl.HistoryServiceImpl;
import com.capstone2.dnsos.utils.FileUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
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

    private final HistoryServiceImpl historyService;
    private final HttpStatusExceptions httpStatusExceptions;

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
            boolean isCheck = historyService.updateStatusHistoryById(request);
            if (!isCheck) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Update status false ", 400, false));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // sos ,  upload
    @PutMapping(value = "/uploads/{historyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(@Valid @PathVariable("historyId") Long historyId, @ModelAttribute List<MultipartFile> files) {
        try {
            // check history
            History existingHistory = historyService.getHistoryById(historyId);
            String[] listFileName;
            if (files != null && !files.isEmpty()) {
                // get list name file save in folder
                listFileName = FileUtil.saveImgAndAudio(files, existingHistory);
                // check type in name
                int i = 0;
                final String[] fileType = {".mp3", ".png"};

                for (String file : listFileName) {
                    String type = FileUtil.getTypeFile(file, fileType);
                    if (type.equals(fileType[0])) {
                        existingHistory.setVoice(file);
                    } else if (i == 0 && fileType[1].equals(type)) {
                        existingHistory.setImage1(file);
                        i++;
                    } else if (i == 1 && fileType[1].equals(type)) {
                        existingHistory.setImage2(file);
                        i++;
                    } else if (i == 2 && fileType[1].equals(type)) {
                        existingHistory.setImage3(file);
                    }
                }
                historyService.uploadMediaHistory(existingHistory);
            } else {
                return ResponseEntity.ok("History does not update media because not have a file");
            }
            return ResponseEntity.ok(listFileName);
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
    public ResponseEntity<?> getHistoriesByUserId(@Valid @PathVariable("id") long userId) {
        try {
            List<ListHistoryByUserResponses> ls = historyService.getHistoriesByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Successfully", 200, ls));
        } catch (NotFoundException e) {
            return httpStatusExceptions.handleNotFoundException(e, e.getMessage());
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