package com.example.bookstorerest.service;

import com.example.bookstorerest.configuration.EncoderConfig;
import com.example.bookstorerest.configuration.JwtAuthenticationResponse;
import com.example.bookstorerest.configuration.SecurityConfiguration;
import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private JwtService jwtService;

    public User save(User user){
        log.debug("saving user {}", user.getUsername());
       return userRepository.save(user);
    }
    public User create(User user){
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            throw new RuntimeException("exist");
        }
        return save(user);
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            log.info("User with id {} was found", id);
            return optionalUser;
        } else {
            log.warn("User id not found or empty");
            return Optional.empty();
        }
    }

    public Optional<User> findByUserName(String username) {
        Optional<User> foundUserName = userRepository.findByUsername(username);
        if (foundUserName.isPresent()) {
            log.info("User with username {} was found",username);
            return foundUserName;
        } else {
            log.warn("UserName not found");
            return Optional.empty();
        }
    }

    public Optional<List<User>> findAll() {
        List<User> allUser = userRepository.findAll();
        return Optional.of(allUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
//    public User getCurrentUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null){
//            log.info("Authentication is authenticated {}", authentication.isAuthenticated());
//            log.info("Authentication getPrincipal {}", authentication.getPrincipal());
//
//            if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails){
//                log.info("User is authenticated");
//                log.info("Principal is instance of UserDetails");
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                log.info(userDetails.getUsername(), " username");
//                log.info("Authentication user {}", userDetails);
//                User user = findByUserName(userDetails.getUsername()).orElse(null);
//                if(user == null){
//                    log.error("User not found with {}", userDetails.getUsername());
//                }
//                return user;
//            }
//            if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof String){
//                log.warn("Principal instance of string {}", authentication.getPrincipal());
//                String username = (String) authentication.getPrincipal();
//                User user = findByUserName(username).orElse(null);
//                if(user == null){
//                    log.error("User with username {} not found", username);
//                }
//                return user;
//            }
//        }else {
//            log.warn("Principal has unknown type " + authentication.getPrincipal().getClass().getName());
//        }
//        log.error("User is not authenticated");
//        throw new RuntimeException("User not authenticated");
//    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            log.error("User not authenticated or not found");
            throw  new RuntimeException("User not found");
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails userDetails){
            return findByUserName(userDetails.getUsername()).orElseThrow(() -> {
                log.error("User not found {}", userDetails.getUsername());
                return new RuntimeException("User not found");
            });
            }
        if(principal instanceof String userName){
            return findByUserName(userName).orElseThrow(() ->{
                log.error("User not found with {}", userName);
                return new RuntimeException("User not found");
            });
        }
        log.error("Unsupported authentication principal type: {}", principal.getClass().getName());
        throw new RuntimeException("Unsupported authentication principal");
    }
    public String regAdmin(User user){
        if(user == null || user.getUsername() == null || user.getPassword()== null){
            log.error("Invalid user data provided");
            throw new IllegalArgumentException("User data is null or incomplete");
        }
        Optional<User> byUserName = findByUserName(user.getUsername());
        if(byUserName.isPresent()){
            log.error(" User with userName {} already exist", user.getUsername());
            throw new IllegalArgumentException("User already exist");
        }
         User newUser = User.builder().
                 username(user.getUsername()).
                 password(encoderConfig.passwordEncoder().encode(user.getPassword())).
                 roles(Set.of("ADMIN")).
                 build();

        try {
            userRepository.save(newUser);
            log.info("User {} elevated to admin was saved", user.getUsername());
            String jwt = jwtService.generateToken(newUser);
            return jwt;
        }catch (Exception e){
            log.error("Error saving user {}: {}", user.getUsername(), e.getMessage());
            throw new RuntimeException("Error updating user details", e);
        }
    }
}
