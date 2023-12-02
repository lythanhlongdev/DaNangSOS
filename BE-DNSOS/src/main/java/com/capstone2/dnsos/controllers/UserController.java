package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.dto.PhoneNumberDTO;
import com.capstone2.dnsos.dto.SecurityDTO;
import com.capstone2.dnsos.dto.UserDTO;
import com.capstone2.dnsos.dto.user.FamilyDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.IHttpError;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
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

    @GetMapping("/family")
    public ResponseEntity<?> getFamilies(@RequestBody @Valid FamilyDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(listError);
            }
            //get phone
            String phoneNumber = request.getPhoneNumber();
            // get user
//            List<User> families = userService.families(phoneNumber);
            // mapper responses
//            List<FamilyResponses> familyResponses = families.stream().map(FamilyResponses::mapperUser).toList();
            return ResponseEntity.badRequest().body("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUserByPhoneNumber(@RequestBody @Valid PhoneNumberDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(listError);
            }
//            UserResponses user = userService.getUserByPhoneNumber(request);
            return ResponseEntity.ok("oke");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/security_code")
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


    // BUG: nếu như đó lài tài khoản đàu tiên thì mã gi đình sẽ là null gặp lỗi null, fig tim cách random mã gia đình
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO request, BindingResult error) {
        try {
            if (error.hasErrors()) {
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("successfully", 200, ""));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/security_code")
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
