package com.example.bookstorerest.controller;

import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(summary = "CurrentUser")
    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getSession(){
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}
