package com.sourav.financedashboardbackend.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateSelfRequest {

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, max = 100, message = "Password should be at least 8 characters")
    private String password;
}
