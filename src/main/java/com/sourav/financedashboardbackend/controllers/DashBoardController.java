package com.sourav.financedashboardbackend.controllers;


import com.sourav.financedashboardbackend.response.ApiResponse;
import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import com.sourav.financedashboardbackend.service.dashboard.DashBoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/total-income")
    public ResponseEntity<ApiResponse> getTotalIncome() {
        double totalIncome = dashBoardService.getTotalIncome();
        return ResponseEntity.status(OK).body(new ApiResponse("Total Income fetched successfully", totalIncome));
    }

    @GetMapping("/total-expense")
    public ResponseEntity<ApiResponse> getTotalExpense() {
        double totalExpense = dashBoardService.getTotalExpense();
        return ResponseEntity.status(OK).body(new ApiResponse("Total Expense fetched successfully", totalExpense));
    }
    @GetMapping("/net-balance")
    public ResponseEntity<ApiResponse> getNetBalance() {
        double netBalance = dashBoardService.getNetBalance();
        return ResponseEntity.status(OK).body(new ApiResponse("Net Balance fetched successfully", netBalance));
    }

    @GetMapping("/category-totals")
    public ResponseEntity<ApiResponse> getCategoryTotals() {
        Map<String, Double> categoryTotals = dashBoardService.getCategoryTotals();
        return ResponseEntity.status(OK).body(new ApiResponse("Category totals fetched successfully", categoryTotals));
    }

    @GetMapping("/recent-activity")
    public ResponseEntity<ApiResponse> getRecentActivity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending) {
        Page<FinanceRecordResponse> recentActivity = dashBoardService.getRecentActivity(page, size, sortBy, ascending);
        return ResponseEntity.status(OK).body(new ApiResponse("Recent activity fetched successfully", recentActivity));
    }

    @GetMapping("/trends")
    public ResponseEntity<ApiResponse> getTrends(
            @RequestParam(defaultValue = "monthly") String period) {

        Map<String, Double> trends = (Map<String, Double>) dashBoardService.getTrends(period);
        return ResponseEntity.status(OK)
                .body(new ApiResponse("Trends fetched successfully", trends));
    }


}
