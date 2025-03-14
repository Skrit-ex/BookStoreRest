package com.example.bookstorerest.controller;

import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/reg")
    public ResponseEntity<String> register(@RequestBody User user) {
        Optional<User> newUser = userRepository.findByUsername(user.getUsername());
        if (newUser.isPresent()) {
            return ResponseEntity.status(400).body("User exist, try again");
        } else {
            userRepository.save(user);
            return ResponseEntity.ok("User was register");
        }
    }

}
