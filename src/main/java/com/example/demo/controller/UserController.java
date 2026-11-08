package com.example.demo.controller;

import com.example.demo.entity.CreateUserByAdminRequest;
import com.example.demo.entity.RegisterUserRequest;
import com.example.demo.entity.UserResponse;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {

        UserResponse userResponse = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> registerByAdmin(@RequestBody CreateUserByAdminRequest createUserByAdminRequest) {

        UserResponse userResponse = userService.createUserOrAdmin(createUserByAdminRequest);
        return ResponseEntity.ok(userResponse);
    }
}
