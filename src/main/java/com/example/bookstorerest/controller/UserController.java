package com.example.bookstorerest.controller;

import com.example.bookstorerest.configuration.JwtAuthenticationResponse;
import com.example.bookstorerest.entity.SignInRequest;
import com.example.bookstorerest.entity.SignUpRequest;
import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
import com.example.bookstorerest.service.AuthenticationService;
import com.example.bookstorerest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;


    @PostMapping("/reg")
    @Operation (summary = "Registration")
    public JwtAuthenticationResponse register(@RequestBody @Valid SignUpRequest request){
            return authenticationService.signUp(request);
    }
    @Operation(summary = "Authentication")
    @PostMapping("/auth")
    public JwtAuthenticationResponse login(@RequestBody @Valid SignInRequest request){
        return authenticationService.signIn(request);
    }
    @Operation(summary = "SaveAdmin")
    @PostMapping("/admin")
    public ResponseEntity <String> endpoint(@RequestBody User user){
        userService.regAdmin(user);
        return ResponseEntity.ok("Saved");
    }

}
