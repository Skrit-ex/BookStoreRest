package com.example.bookstorerest.controller;

import com.example.bookstorerest.configuration.JwtAuthenticationResponse;
import com.example.bookstorerest.entity.SignInRequest;
import com.example.bookstorerest.entity.SignUpRequest;
import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
import com.example.bookstorerest.service.AuthenticationService;
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

//    @Operation(summary = "Регистрация пользователя")
//    @PostMapping("/reg")
//    public ResponseEntity<String> register(@RequestBody User user) {
//        Optional<User> newUser = userRepository.findByUsername(user.getUsername());
//        if (newUser.isPresent()) {
//            return ResponseEntity.status(400).body("User exist, try again");
//        } else {
//            user.setRoles(Set.of("USER"));
//            userRepository.save(user);
//            return ResponseEntity.ok("User was register");
//        }
//    }

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
//    @Operation(summary = "GetCurrentUser")
//    public JwtAuthenticationResponse getCurrentUser(){
//        return authenticationService.
//    }


}
