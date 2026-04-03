package com.sourav.financedashboardbackend.service.financeRecord;

import com.sourav.financedashboardbackend.RequestDto.FRecordUpdateRequest;
import com.sourav.financedashboardbackend.RequestDto.FinanceRecordRequest;
import com.sourav.financedashboardbackend.model.FinanceRecord;
import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinanceRecordService {

    FinanceRecordResponse createRecord(FinanceRecordRequest request);
    Page<FinanceRecordResponse> getAllRecords(Pageable pageable);
    FinanceRecordResponse getRecordById(Long recordId);
    FinanceRecordResponse updateRecord(Long recordId, FRecordUpdateRequest request);
    void deleteRecord(Long recordId);
    void softDelete(Long recordId);
    void restoreRecord(Long recordId);
    FinanceRecordResponse findSoftDeleted(Long softDeletedId);

    Page<FinanceRecordResponse> findAllSoftDeleted(Pageable pageable);

    Page<FinanceRecordResponse> getRecordsByType(String type,Pageable pageable);
    Page<FinanceRecordResponse> getRecordsByCategory(String category, Pageable pageable);
    Page<FinanceRecordResponse> getRecordsByDateRange(LocalDate start, LocalDate end, Pageable pageable);
    List<FinanceRecordResponse> filterRecords(String type, String category, LocalDate startDate, LocalDate endDate);
    List<FinanceRecordResponse> searchRecords(String keyword);

}