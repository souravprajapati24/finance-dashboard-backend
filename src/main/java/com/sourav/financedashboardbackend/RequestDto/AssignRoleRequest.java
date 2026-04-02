package com.sourav.financedashboardbackend.RequestDto;

import com.sourav.financedashboardbackend.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignRoleRequest {
    @NotNull(message = "Role should not be null")
    private Role role;
}
