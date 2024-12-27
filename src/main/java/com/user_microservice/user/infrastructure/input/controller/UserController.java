package com.user_microservice.user.infrastructure.input.controller;

import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.application.dto.user_dto.UserResponse;
import com.user_microservice.user.application.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserHandler userHandler;

    @PostMapping("/crear")
    public ResponseEntity<UserResponse> resgisterUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse registerUser = userHandler.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }


}
