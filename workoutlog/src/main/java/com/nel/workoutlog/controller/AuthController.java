package com.nel.workoutlog.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.nel.workoutlog.entity.User;
import com.nel.workoutlog.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        if (!passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
