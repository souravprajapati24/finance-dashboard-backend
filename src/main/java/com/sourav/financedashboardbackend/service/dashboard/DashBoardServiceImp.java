package com.sourav.financedashboardbackend.service.dashboard;

import com.sourav.financedashboardbackend.model.FinanceRecord;
import com.sourav.financedashboardbackend.repositories.FinanceRecordRepository;
import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import com.sourav.financedashboardbackend.service.financeRecord.FinanceRecordServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DashBoardServiceImp implements DashBoardService {

    private final FinanceRecordRepository recordRepository;
    private final FinanceRecordServiceImp financeRecordService;


    @Override
    public double getTotalIncome() {
        List<FinanceRecord> records = recordRepository.findAll();
        return records.stream()
                .filter(record->record.getType().equalsIgnoreCase("income"))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();
    }

    @Override
    public double getTotalExpense() {
        List<FinanceRecord> records = recordRepository.findAll();
        return records.stream()
                .filter(record->record.getType().equalsIgnoreCase("expense"))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();
    }

    @Override
    public double getNetBalance() {
        return getTotalIncome()-getTotalExpense();
    }

    @Override
    public Map<String, Double> getCategoryTotals() {
        List<Object[]> results = recordRepository.findCategoryTotals();
        return results.stream().collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> ((Number) r[1]).doubleValue()));
    }

    @Override
    public Page<FinanceRecordResponse> getRecentActivity(int page, int size, String sortBy, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return recordRepository.findByDeletedFalse(pageable)
                .map(financeRecordService::convertToRecordDto);
    }

    @Override
    public Map<String, Double> getTrends(String period) {
        List<FinanceRecord> records = recordRepository.findAllByDeletedFalse();

        if ("weekly".equalsIgnoreCase(period)) {
            WeekFields weekFields = WeekFields.ISO;
            return records.stream().collect(Collectors.groupingBy(
                            r -> r.getDate().getYear() + "-W" + r.getDate().get(weekFields.weekOfWeekBasedYear()),
                            Collectors.summingDouble(r -> r.getType().equalsIgnoreCase("income") ? r.getAmount() : -r.getAmount())));
        } else {
            return records.stream().collect(Collectors.groupingBy(
                            r -> YearMonth.from(r.getDate()).toString(),
                            Collectors.summingDouble(r -> r.getType().equalsIgnoreCase("income") ? r.getAmount() : -r.getAmount())));
        }
    }


}
