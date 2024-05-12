package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.rescue.IRescueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                new ResponsesEntity("Đăng Ký Nhân Viên Thành Công", HttpStatus.CREATED.value(), rescueResponse));
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


    @PreAuthorize("hasAnyRole('ROLE_RESCUE_WORKER')")
    @PatchMapping("/gps")
    public ResponseEntity<?> updateGPS(@Valid @RequestBody GpsDTO request, BindingResult result) throws Exception {
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
        RescueByHistoryResponse rescueResponse = rescueService.updateGPS(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Update GPS successfully", HttpStatus.OK.value(), rescueResponse));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @GetMapping("")
    public ResponseEntity<?> getAllRescueWorker(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
            Page<PageRescueWorkerResponse> rescueWorkerPage = rescueService.getAllRescueWorker(pageable);
            int totalPages = rescueWorkerPage.getTotalPages();
            long totalElements = rescueWorkerPage.getTotalElements();
            PageRescueWorkerResponses pageRescueWorkerResponses = PageRescueWorkerResponses.builder()
                    .listRescueWorker(rescueWorkerPage.getContent())
                    .totalPages(totalPages)
                    .totalElements(totalElements)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lấy danh sách tất cả nhân viên cứu hộ thành công", HttpStatus.OK.value(), pageRescueWorkerResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailWorkerById(@PathVariable("id") Long workerId) {
        try {
            DetailRescueWorkerResponse detailRescueWorkerById = rescueService.getDetailRescueWorkerById(workerId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lây thông tin nhân viên thành công", HttpStatus.OK.value(), detailRescueWorkerById));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllRescueWorkerForAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
            Page<PageRescueWorkerResponse> rescueWorkerPage = rescueService.getAllRescueWorkerForAdmin(pageable);
            int totalPages = rescueWorkerPage.getTotalPages();
            long totalElements = rescueWorkerPage.getTotalElements();

            PageRescueWorkerResponses pageRescueWorkerResponses = PageRescueWorkerResponses.builder()
                    .listRescueWorker(rescueWorkerPage.getContent())
                    .totalPages(totalPages)
                    .totalElements(totalElements)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lấy danh sách tất cả nhân viên cứu hộ thành công", HttpStatus.OK.value(), pageRescueWorkerResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<?> getDetailWorkerByIdForAdmin(@PathVariable("id") Long workerId) {
        try {
            DetailRescueWorkerResponse detailRescueWorkerById = rescueService.getDetailRescueWorkerByIdForAdmin(workerId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lây thông tin nhân viên thành công", HttpStatus.OK.value(), detailRescueWorkerById));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }


    @PreAuthorize("hasRole('ROLE_RESCUE_STATION')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRescueWorker(@PathVariable("id") Long rescueWorkerId) {
        try {
            rescueService.deleteRescueWorker(rescueWorkerId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Xóa nhân vên cứu hộ thành công với mã số: " + rescueWorkerId, HttpStatus.OK.value(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

}
