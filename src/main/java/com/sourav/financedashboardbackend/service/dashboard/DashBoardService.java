package com.sourav.financedashboardbackend.service.dashboard;

import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DashBoardService {

    double getTotalIncome();
    double getTotalExpense();
    double getNetBalance();
    Map<String, Double> getCategoryTotals();
    Page<FinanceRecordResponse> getRecentActivity(int page, int size, String sortBy, boolean ascending);
    Map<?, Double> getTrends(String period);
}
