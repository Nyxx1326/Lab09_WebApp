package com.example.customerapi.controller;

import com.example.customerapi.dto.UpdateProfileDTO;
import com.example.customerapi.dto.UserResponseDTO;
import com.example.customerapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // =======================
    // EX 7.1 – VIEW PROFILE
    // =======================
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(userService.getCurrentUser(username));
    }

    // =======================
    // EX 7.2 – UPDATE PROFILE
    // =======================
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @Valid @RequestBody UpdateProfileDTO dto) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(userService.updateProfile(username, dto));
    }

    // =======================
    // EX 7.3 – DELETE ACCOUNT
    // =======================
    @DeleteMapping("/account")
    public ResponseEntity<?> deleteAccount(@RequestParam String password) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.deleteAccount(username, password);
    }

    
}
