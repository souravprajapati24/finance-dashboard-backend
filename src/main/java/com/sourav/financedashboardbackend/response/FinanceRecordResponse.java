package com.sourav.financedashboardbackend.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FinanceRecordResponse {

    private Long id;
    private Double amount;
    private String type;
    private String category;
    private LocalDate date;
    private String description;
}
