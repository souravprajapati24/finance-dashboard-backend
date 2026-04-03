package com.sourav.financedashboardbackend.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinanceRecordRequest {

    @NotNull(message = "Amount is required!")
    @Positive(message = "Amount should be greater than zero!")
    private Double amount;

    @NotBlank(message = "Type is required!")
    private String type;

    @NotBlank(message = "Category is required!")
    private String category;

    @NotNull(message = "Date is required!")
    private LocalDate date;
    private String description;
}
