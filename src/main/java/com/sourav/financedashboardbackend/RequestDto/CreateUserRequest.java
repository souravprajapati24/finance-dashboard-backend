package com.sourav.financedashboardbackend.RequestDto;

import com.sourav.financedashboardbackend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NotNull(message = "Role must be provided")
    private Role role;

}
