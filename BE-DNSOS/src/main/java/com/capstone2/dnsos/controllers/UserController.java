package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.responses.FamilyResponses;
import com.capstone2.dnsos.responses.ResponsesEntity;
import com.capstone2.dnsos.responses.UserResponses;
import com.capstone2.dnsos.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserServiceImpl userService;
//    private final IHttpError httpError;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errMessage = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errMessage);
            }

            return ResponseEntity.ok("Login oke");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login false");
        }
    }

    // BUG: nếu như đó lài tài khoản đàu tiên thì mã gi đình sẽ là null gặp lỗi null, fig tim cách random mã gia đình
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO request, BindingResult result) {
        try {
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
            User user = userService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Register Successfully", HttpStatus.OK.value(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error register " + e.getMessage());
        }
    }


    @GetMapping("/families/{phone_number}")
    public ResponseEntity<?> getAllUserByFamily(@PathVariable("phone_number") String request) {
        try {
            List<FamilyResponses> list = userService.getAllUserByFamily(request);
            return ResponseEntity.badRequest().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{phone_number}")
    public ResponseEntity<?> getUserByPhoneNumber(@Valid @PathVariable("phone_number") String request) {
        try {
            UserResponses user = userService.getUserByPhoneNumber(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            User user = userService.updateSecurityCode(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update Security successfully", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // BUG: để ý lại 11/12/2023
    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            UserResponses userResponses = userService.updateUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("successfully", 200, userResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping ("/security_code")
    public ResponseEntity<?> getSecurityCodeByPhoneNumber(@RequestBody @Valid SecurityDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            boolean securityCode = userService.getSecurityCodeByPhoneNumber(request);
//            String mess = securityCode ? "True" : "False";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("successfully", 200, securityCode));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity(e.getMessage(), 400, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
