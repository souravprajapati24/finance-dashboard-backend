package com.sourav.financedashboardbackend.response;

import com.sourav.financedashboardbackend.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean status;
}
