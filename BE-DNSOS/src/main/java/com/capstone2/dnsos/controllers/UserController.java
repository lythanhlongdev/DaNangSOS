package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.component.LocalizationUtils;
import com.capstone2.dnsos.dto.PasswordDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.Token;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.tokens.ITokenService;
import com.capstone2.dnsos.services.users.IUserAuthService;
import com.capstone2.dnsos.services.users.IUserReadService;
import com.capstone2.dnsos.services.users.IUserUpdateDeleteService;
import com.capstone2.dnsos.utils.FileUtil;
import com.capstone2.dnsos.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserAuthService userAuthService;
    private final IUserReadService userReadService;
    private final IUserUpdateDeleteService userUpdateDeleteService;
    private final ITokenService tokenService;
    private final LocalizationUtils localizationUtils;

    private final static String URL_AVATAR = "./avatar/users/";
    private final static String URL_IMG_NOT_FOUND = "image_http_status_error/404/avatar_notfound.jpeg";

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                List<String> errMessage =
                        result.getAllErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(new ResponsesEntity(errMessage.toString(), HttpStatus.BAD_REQUEST.value(), ""));
            }

            String token = userAuthService.login(loginDTO);

            String userAgent = request.getHeader("User-Agent");
            User userDetail = userAuthService.getUserDetailsFromToken(token);
            Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

            LoginResponse loginResponse = LoginResponse.builder()
                    .message(MessageKeys.LOGIN_SUCCESSFULLY)
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
//                    .refreshToken(jwtToken.getRefreshToken())
                    .username(userDetail.getUsername())
                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                    .id(userDetail.getId())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Token", 200, loginResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Login failed!", 400, e.getMessage()));
        }
    }

    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        // Ví dụ đơn giản:
        return userAgent.toLowerCase().contains("mobile");
    }

    // BUG: nếu như đó lài tài khoản đàu tiên thì mã gi đình sẽ là null gặp lỗi null, fig tim cách random mã gia đình
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO request, BindingResult result) {
        try {
            logger.info("\nStart register role user........................");
            logger.info("\nPOST: user/register");
            logger.info("${}", request.toString());
//            GetErorrInRequest.errMessage(result);
            if (result.hasErrors()) {
                List<String> errMessage = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(errMessage.toString(), 400, ""));
            }
            // check match
            if (!request.getPassword().equals(request.getRetypePassword())) {
                throw new InvalidParamException("Password not match");
            }
            User user = userAuthService.register(request);
            logger.info("\nStop register user....................");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Register Successfully", HttpStatus.OK.value(), null));
        } catch (Exception e) {
            logger.error("User register:{} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Error register " + e.getMessage(), 400, ""));
        }
    }

    //    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_RESCUE')")
    @GetMapping("/families/{phone_number}")
    public ResponseEntity<?> getAllFamiliesByPhoneNumber(@PathVariable("phone_number") String request) {
        try {
            List<FamilyResponses> list = userReadService.getAllUserByFamily(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get family successfully", 200, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getUserByPhoneNumber() {
        try {
            UserNotPasswordResponses user = userReadService.getUserByPhoneNumber();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get user successfully", 200, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

//    @PatchMapping("/security_code")
//    public ResponseEntity<?> updateSecurityCode(@RequestBody @Valid SecurityDTO request, BindingResult error) {
//        try {
//            if (error.hasErrors()) {
//                List<String> listError = error.getAllErrors()
//                        .stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Cannot update security code", 400, ""));
//            }
//            User user = userUpdateDeleteService.updateSecurityCode(request);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update Security successfully", 200, user.getSecurityCode()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
//        }
//    }


    // BUG: để ý lại 11/12/2023

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            UserNotPasswordResponses userNotPasswordResponses = userUpdateDeleteService.updateUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update User successfully", 200, userNotPasswordResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

//    @PostMapping("/security_code")
//    public ResponseEntity<?> getSecurityCodeByPhoneNumber( @Valid @RequestBody SecurityDTO securityDTO,  BindingResult error) {
//        try {
//            if (error.hasErrors()) {
//                List<String> listError = error.getAllErrors()
//                        .stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
//            }
//            boolean securityCode = userReadService.getSecurityCodeByPhoneNumber(securityDTO);
////            String mess = securityCode ? "True" : "False";
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("successfully", 200, securityCode));
//        } catch (NullPointerException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
//        }
//    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO, BindingResult error, HttpServletRequest request) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            if (!passwordDTO.getNewPassword().equals(passwordDTO.getRetypeNewPassword())) {
                throw new InvalidParamException("Password not match");
            }
            String token = userUpdateDeleteService.ChangePassword(passwordDTO);

            String userAgent = request.getHeader("User-Agent");
            User userDetail = userAuthService.getUserDetailsFromToken(token);
            Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

            LoginResponse loginResponse = LoginResponse.builder()
                    .message(MessageKeys.LOGIN_SUCCESSFULLY)
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
//                    .refreshToken(jwtToken.getRefreshToken())
                    .username(userDetail.getUsername())
                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                    .id(userDetail.getId())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Change Password successfully", 200, loginResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


    @GetMapping("/forgot_password/{phone_number}")
    public ResponseEntity<?> forgotPassword(@PathVariable("phone_number") String phoneNumber, HttpServletRequest request) {
        try {
            String newPass = userUpdateDeleteService.forgotPassword(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Forgot password successfully", 200, newPass));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/{user_phone_number}/lock")
    public ResponseEntity<?> lockUser(@Valid @PathVariable("user_phone_number") String phoneNumber) {
        try {
            userAuthService.lockUser(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lock User successfully", 200, ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/{user_phone_number}/unlock")
    public ResponseEntity<?> unLockUser(@Valid @PathVariable("user_phone_number") String phoneNumber) {
        try {
            userAuthService.unLockUser(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("UnLock successfully", 200, ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllUserForAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            // Tạo Pageable từ thông tin trang và giới hạn
            //Sort.by("createdAt").descending()
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
            Page<PageUserResponse> userPages = userReadService.getAllUser(pageRequest);
            UserPageResponses userPageResponses = UserPageResponses.builder()
                    .userNotPasswordResponses(userPages.getContent())
                    .totalPage(userPages.getTotalPages())
                    .totalElements(userPages.getTotalElements())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get All User successfully", 200, userPageResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<?> getDetailUserById(@PathVariable("id") Long userId) {
        try {
            DetailUserResponse detailUserResponse = userReadService.getDetailUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Lấy thông tin người dùng thành công", HttpStatus.OK.value(), detailUserResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@ModelAttribute MultipartFile avatar) throws Exception {
        if (avatar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("File empty", 400, ""));
        }
        UserResponse userNotPasswordResponses = userUpdateDeleteService.updateAvatar(avatar);
        return ResponseEntity.ok(userNotPasswordResponses);
    }

    @GetMapping("/avatar")
    public ResponseEntity<?> getAvatar() throws Exception {
        try {
            AvatarResponse avatarName = userReadService.getAvatar();
            java.nio.file.Path imagePath = Paths.get(String.format("%s/%s", URL_AVATAR, avatarName.getAvatarName()));
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

    @GetMapping("/avatar/{avatar_name}")
    public ResponseEntity<?> getAvatar(@PathVariable("avatar_name") String avatar_name) throws Exception {
        try {
            java.nio.file.Path imagePath = Paths.get(URL_AVATAR + avatar_name);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                MediaType mediaType = FileUtil.getMediaType(resource);
                return ResponseEntity.ok().contentType(mediaType).body(resource);
            } else {
                Resource notFoundResource = new ClassPathResource(URL_IMG_NOT_FOUND);
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
