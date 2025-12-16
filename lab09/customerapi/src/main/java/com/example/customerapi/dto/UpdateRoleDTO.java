package com.example.customerapi.dto;

import com.example.customerapi.entity.Role;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleDTO {

    @NotNull
    private Role role;

    // getter & setter
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    
}
