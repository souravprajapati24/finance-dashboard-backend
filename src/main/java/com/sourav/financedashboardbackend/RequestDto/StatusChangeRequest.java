package com.sourav.financedashboardbackend.RequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


@Data
public class StatusChangeRequest {
    @NotNull(message = "Status should not be null")
    private boolean newStatus;
}
