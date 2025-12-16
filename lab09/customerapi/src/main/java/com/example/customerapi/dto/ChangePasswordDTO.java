package com.example.customerapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ChangePasswordDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 6)
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
