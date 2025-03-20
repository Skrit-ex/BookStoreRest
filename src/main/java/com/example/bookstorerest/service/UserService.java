package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
