package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.history.HistoryDTO;
import jakarta.validation.Valid;
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
@RequestMapping("${api.prefix}/histories")
public class HistoryController {


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
            return ResponseEntity.ok("oke");
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