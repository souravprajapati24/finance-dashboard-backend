package com.sourav.financedashboardbackend.controllers;

import com.sourav.financedashboardbackend.RequestDto.FRecordUpdateRequest;
import com.sourav.financedashboardbackend.RequestDto.FinanceRecordRequest;
import com.sourav.financedashboardbackend.response.ApiResponse;
import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import com.sourav.financedashboardbackend.service.financeRecord.FinanceRecordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/records")
public class FinanceController {

    private final FinanceRecordService financeRecordService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> getAllRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("date").descending()
        );
        Page<FinanceRecordResponse> records =
                financeRecordService.getAllRecords(pageable);
        return ResponseEntity.status(OK).body(new ApiResponse("Records fetched successfully", records));
    }

    @GetMapping("/record/{id}")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> getRecordById(@PathVariable Long id){
        FinanceRecordResponse response = financeRecordService.getRecordById(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Record fetched By id Successfully",response));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createRecord(
            @Valid @RequestBody FinanceRecordRequest request) {
        FinanceRecordResponse response = financeRecordService.createRecord(request);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Record created successfully", response));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateRecord(@PathVariable Long id,
            @Valid @RequestBody FRecordUpdateRequest request) {
        FinanceRecordResponse updatedRecord = financeRecordService.updateRecord(id, request);
        return ResponseEntity.status(OK).body((new ApiResponse("Record updated successfully", updatedRecord)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteRecord(@PathVariable Long id) {
        financeRecordService.deleteRecord(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Record deleted successfully", null));
    }

    @PatchMapping("/soft-delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> softDelete(@PathVariable Long id) {
        financeRecordService.softDelete(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Record soft deleted successfully", null));
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> restoreRecord(@PathVariable Long id) {
        financeRecordService.restoreRecord(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Record restored successfully", null));
    }

    @GetMapping("/deleted/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getSoftDeletedRecord(@PathVariable Long id) {
        FinanceRecordResponse record = financeRecordService.findSoftDeleted(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Soft deleted record fetched successfully", record));
    }

    @GetMapping("/soft-deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllSoftDeletedRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<FinanceRecordResponse> records = financeRecordService.findAllSoftDeleted(pageable);
        return ResponseEntity.status(OK).body(new ApiResponse("Soft deleted records fetched successfully", records));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> getRecordsByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<FinanceRecordResponse> records = financeRecordService.getRecordsByType(type, pageable);
        return ResponseEntity.status(OK).body(new ApiResponse("Records fetched successfully", records));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> getRecordsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<FinanceRecordResponse> records = financeRecordService.getRecordsByCategory(category, pageable);
        return ResponseEntity.status(OK).body(new ApiResponse("Records fetched successfully", records));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> getRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<FinanceRecordResponse> records = financeRecordService.getRecordsByDateRange(start, end, pageable);
        return ResponseEntity.status(OK).body(new ApiResponse("Records fetched successfully", records));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> filterRecords(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<FinanceRecordResponse> records = financeRecordService.filterRecords(type, category, startDate, endDate);

        return ResponseEntity.status(OK).body(new ApiResponse("Filtered records fetched successfully", records));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse> searchRecords(@RequestParam String keyword) {
        List<FinanceRecordResponse> records = financeRecordService.searchRecords(keyword);
        return ResponseEntity.status(OK).body(new ApiResponse(
                        records.isEmpty() ? "No records found" : "Records fetched successfully", records));
    }


}
