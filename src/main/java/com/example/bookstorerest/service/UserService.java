package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.UserRepository;
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
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            log.info("User with id '" + id + "' was found");
            return optionalUser;
        } else {
            log.warn("User id not found or empty");
            return Optional.empty();
        }
    }

    public Optional<User> findByUserName(String username) {
        Optional<User> foundUserName = userRepository.findByUsername(username);
        if (foundUserName.isPresent()) {
            log.info("User with username '" + username + "' was found");
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
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("USer not found in details");
    }
}
