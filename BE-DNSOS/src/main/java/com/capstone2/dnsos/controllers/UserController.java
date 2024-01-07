package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.FamilyResponses;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.responses.main.UserResponses;
import com.capstone2.dnsos.services.users.IUserAuthService;
import com.capstone2.dnsos.services.users.IUserReadService;
import com.capstone2.dnsos.services.users.IUserUpdateDeleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserAuthService userAuthService;
    private final IUserReadService userReadService;
    private final IUserUpdateDeleteService userUpdateDeleteService;
    private  final IUserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errMessage = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errMessage);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed!");
        }
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
                return ResponseEntity.badRequest().body(errMessage);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Error register " + e.getMessage(),400,""));
        }
    }


    @GetMapping("/families/{phone_number}")
    public ResponseEntity<?> getAllUserByFamily(@PathVariable("phone_number") String request) {
        try {
            List<FamilyResponses> list = userReadService.getAllUserByFamily(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get familly successfully",200,list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @GetMapping("/{phone_number}")
    public ResponseEntity<?> getUserByPhoneNumber(@Valid @PathVariable("phone_number") String request) {
        try {
            UserResponses user = userReadService.getUserByPhoneNumber(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get user successfully",200,user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @PutMapping("/security_code")
    public ResponseEntity<?> updateSecurityCode(@RequestBody @Valid SecurityDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Cannot update security code", 400, ""));
            }
            User user = userUpdateDeleteService.updateSecurityCode(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update Security successfully", 200, user.getSecurityCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }


    // BUG: để ý lại 11/12/2023

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            UserResponses userResponses = userUpdateDeleteService.updateUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update User successfully", 200, userResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

    @PostMapping("/security_code")
    public ResponseEntity<?> getSecurityCodeByPhoneNumber(@RequestBody @Valid SecurityDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            boolean securityCode = userReadService.getSecurityCodeByPhoneNumber(request);
//            String mess = securityCode ? "True" : "False";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("successfully", 200, securityCode));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
        }
    }

}
