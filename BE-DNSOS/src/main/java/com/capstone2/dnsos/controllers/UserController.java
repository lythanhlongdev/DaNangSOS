package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.dto.user.FamilyDTO;
import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.exceptions.DataNotFoundException;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.responses.FamilyResponses;
import com.capstone2.dnsos.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserServiceImpl userService;

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
                throw new DataNotFoundException("Password not match");
            }
            User user = userService.register(request);
            return ResponseEntity.ok().body(user);
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
    public ResponseEntity<?> getFamilies(@RequestBody @Valid FamilyDTO request,BindingResult error){
        try {
            if (error.hasErrors()){
                List<String> listError = error.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(listError);
            }
            //get phone
            String phoneNumber =  request.getPhoneNumber();
            // get user
            List<User> families =  userService.families(phoneNumber);
            // mapper responses
            List<FamilyResponses> familyResponses = families.stream().map(FamilyResponses::mapperUser).toList();
            return ResponseEntity.badRequest().body(familyResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
