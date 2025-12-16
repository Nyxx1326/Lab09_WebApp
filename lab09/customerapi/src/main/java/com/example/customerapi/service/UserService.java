package com.example.customerapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.customerapi.dto.ChangePasswordDTO;
import com.example.customerapi.dto.LoginRequestDTO;
import com.example.customerapi.dto.LoginResponseDTO;
import com.example.customerapi.dto.RegisterRequestDTO;
import com.example.customerapi.dto.UpdateProfileDTO;
import com.example.customerapi.dto.UserResponseDTO;
import com.example.customerapi.entity.Role;

public interface UserService {
    
    LoginResponseDTO login(LoginRequestDTO loginRequest);
    
    UserResponseDTO register(RegisterRequestDTO registerRequest);
    
    UserResponseDTO getCurrentUser(String username);

    ResponseEntity<?> changePassword(ChangePasswordDTO dto);
    
    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> resetPassword(String token, String newPassword);

    UserResponseDTO updateProfile(String username, UpdateProfileDTO dto);

    ResponseEntity<?> deleteAccount(String username, String password);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUserRole(Long id, Role role);

    UserResponseDTO toggleUserStatus(Long id);


}
