package com.anarmitra.backend.controller;

import com.anarmitra.backend.dto.AuthResponse;
import com.anarmitra.backend.dto.LoginRequest;
import com.anarmitra.backend.dto.MessageResponse;
import com.anarmitra.backend.dto.RegisterRequest;
import com.anarmitra.backend.entity.User;
import com.anarmitra.backend.repository.UserRepository;
import com.anarmitra.backend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already registered"));
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        savedUser.getId(),
                        savedUser.getFullName(),
                        savedUser.getEmail(),
                        savedUser.getRole().name()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email or password"));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }
}