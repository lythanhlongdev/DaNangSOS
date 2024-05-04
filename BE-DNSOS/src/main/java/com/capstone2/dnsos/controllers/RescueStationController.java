package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import com.capstone2.dnsos.utils.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/rescue_stations")
public class RescueStationController {

    private final IRescueStationAuthService rescueStationService;
    private final static String URL_AVATAR = "./avatar/rescue_stations";
    private final String[] ROLES = {"ROLE_ADMIN", "ROLE_RESCUE_STATION", "ROLE_USER"};

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RescueStationDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            if (!request.getPassword().equals(request.getRetypePassword())) {
                throw new InvalidParamException("Password not match");
            }
            RescueStationResponses newR = rescueStationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Register new Rescue Station successfully", 200, newR));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasRole('ROLE_RESCUE_STATION')")
    @GetMapping()
    public ResponseEntity<?> getInfoRescue() {
        try {
            RescueStationResponses rescue = rescueStationService.getInfoRescue();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get Rescue Station successfully", 200, rescue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasRole('ROLE_RESCUE_STATION')")
    @PatchMapping()
    public ResponseEntity<?> changeInfoRescue(@Valid @RequestBody UpdateRescueDTO updateRescueDTO, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            RescueStationResponses rescue = rescueStationService.UpdateInfoRescue(updateRescueDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get Rescue Station successfully", 200, rescue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getListRescue(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
            List<RescueForAdminResponses> rescue = rescueStationService.getAllRecue(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get All Rescue Station successfully", 200, rescue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESCUE_STATION')")
    @PatchMapping("{id}/status/{statusId}")
    public ResponseEntity<?> changStatus(@PathVariable("id") Long rescueStationId, @PathVariable("statusId") int status) {
        try {
            RescueStationResponses rescueStationResponses = rescueStationService.updateStatus(rescueStationId, status);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update status rescue station successfully", HttpStatus.OK.value(), rescueStationResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }



    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@ModelAttribute MultipartFile avatar) throws Exception {
        if (avatar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("File empty", 400, ""));
        }
        RescueStationResponses rescueStationResponses = rescueStationService.updateAvatar(avatar);
        return ResponseEntity.ok(rescueStationResponses);
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    
    @GetMapping("/avatar")
    public ResponseEntity<?> getAvatar() throws Exception {
        try {
            AvatarResponse avatarName = rescueStationService.getAvatar();
            java.nio.file.Path imagePath = Paths.get(String.format("%s/%s/%s", URL_AVATAR, avatarName.getUserId(), avatarName.getAvatarName()));
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (!avatarName.getAvatarName().isEmpty() && resource.exists()) {
                MediaType mediaType = FileUtil.getMediaType(resource);
                return ResponseEntity.ok().contentType(mediaType).body(resource);
            } else {
                Resource notFoundResource = new ClassPathResource("image_http_status_error/404/avatar_notfound.jpeg");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(notFoundResource);
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.NOT_FOUND.value(), ""));
        }
    }
}
