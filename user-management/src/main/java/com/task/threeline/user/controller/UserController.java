package com.task.threeline.user.controller;

import com.task.threeline.user.constant.ApiRoute;
import com.task.threeline.user.dto.GeneralResponse;
import com.task.threeline.user.dto.LoginRequest;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid UserRequest user) {
        GeneralResponse response = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
            GeneralResponse response = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<GeneralResponse> findOne(@PathVariable("user-id") Long userId) {
        GeneralResponse response = userService.findOne(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiRoute.FIND_BY_EMAIL + "/{email}")
    public ResponseEntity<GeneralResponse> findByEmail(@PathVariable String email) {
        GeneralResponse response = userService.findByEmail(email);
        return ResponseEntity.ok(response);
    }
}