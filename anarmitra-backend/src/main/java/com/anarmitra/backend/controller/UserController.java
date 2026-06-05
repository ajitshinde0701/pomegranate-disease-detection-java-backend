package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.Role;
import com.anarmitra.backend.entity.User;
import com.anarmitra.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @GetMapping("/users/counts")
    public Map<String, Long> getUserCounts() {
        Map<String, Long> counts = new HashMap<>();

        counts.put("farmers", userRepository.countByRole(Role.FARMER));
        counts.put("merchants", userRepository.countByRole(Role.MERCHANT));
        counts.put("advisors", userRepository.countByRole(Role.ADVISOR));
        counts.put("fertilizerStores", userRepository.countByRole(Role.FERTILIZER_STORE));
        counts.put("admins", userRepository.countByRole(Role.ADMIN));

        return counts;
    }

    @GetMapping("/farmers")
    public List<User> getFarmers() {
        return userRepository.findByRole(Role.FARMER);
    }

    @GetMapping("/merchants")
    public List<User> getMerchants() {
        return userRepository.findByRole(Role.MERCHANT);
    }

    @GetMapping("/advisors")
    public List<User> getAdvisors() {
        return userRepository.findByRole(Role.ADVISOR);
    }

    @GetMapping("/fertilizer-stores")
    public List<User> getFertilizerStores() {
        return userRepository.findByRole(Role.FERTILIZER_STORE);
    }
}