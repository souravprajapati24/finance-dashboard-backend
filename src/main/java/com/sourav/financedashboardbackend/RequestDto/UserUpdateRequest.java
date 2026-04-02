package com.sourav.financedashboardbackend.RequestDto;

import com.sourav.financedashboardbackend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    private Role role;
    private Boolean active;
}
