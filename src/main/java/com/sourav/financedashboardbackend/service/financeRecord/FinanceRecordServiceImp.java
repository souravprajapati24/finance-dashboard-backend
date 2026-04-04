package com.sourav.financedashboardbackend.service.financeRecord;

import com.sourav.financedashboardbackend.RequestDto.FRecordUpdateRequest;
import com.sourav.financedashboardbackend.RequestDto.FinanceRecordRequest;
import com.sourav.financedashboardbackend.exceptions.RecordNotFoundException;
import com.sourav.financedashboardbackend.model.FinanceRecord;
import com.sourav.financedashboardbackend.repositories.FinanceRecordRepository;
import com.sourav.financedashboardbackend.response.FinanceRecordResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class FinanceRecordServiceImp implements FinanceRecordService{
    private final ModelMapper mapper;
    private final FinanceRecordRepository financeRecordRepository;

    @Override
    public FinanceRecordResponse createRecord(FinanceRecordRequest request) {
        return Optional.of(request)
               .map(r -> {
                   FinanceRecord record = new FinanceRecord();
                   record.setAmount(r.getAmount());
                   record.setType(r.getType());
                   record.setCategory(r.getCategory());
                   record.setDate(r.getDate());
                   record.setDescription(r.getDescription());
                   record.setDeleted(false);
                   return financeRecordRepository.save(record);
               }).map(this::convertToRecordDto).orElseThrow();
    }

    @Override
    public Page<FinanceRecordResponse> getAllRecords(Pageable pageable) {
        return financeRecordRepository.findByDeletedFalse(pageable)
                .map(this::convertToRecordDto);
    }
    @Override
    public FinanceRecordResponse getRecordById(Long recordId) {
        return convertToRecordDto(financeRecordRepository.findById(recordId)
                .filter(record->!record.getDeleted())
                .orElseThrow(()->new RecordNotFoundException("Record Not found with id:"+recordId)));

    }

    @Override
    public FinanceRecordResponse updateRecord(Long recordId, FRecordUpdateRequest request) {
        return financeRecordRepository.findById(recordId)
                .filter(record -> !record.getDeleted())
                .map(record -> {
                    record.setAmount(request.getAmount());
                    record.setType(request.getType());
                    record.setCategory(request.getCategory());
                    record.setDate(request.getDate());
                    record.setDescription(request.getDescription());
                    return financeRecordRepository.save(record);
                })
                .map(this::convertToRecordDto)
                .orElseThrow(() -> new RecordNotFoundException("Record not found with id: " + recordId));
    }

    @Override
    public void deleteRecord(Long recordId) {
        financeRecordRepository.findById(recordId)
                .ifPresentOrElse(financeRecordRepository::delete,()->{
                    throw new RecordNotFoundException("Record not found with id: " + recordId);
                });
    }

    @Override
    public void softDelete(Long recordId) {
        financeRecordRepository.findById(recordId)
                .filter(record->!record.getDeleted())
                .map(record->{
                    record.setDeleted(true);
                    return financeRecordRepository.save(record);
                }).orElseThrow(()->new RecordNotFoundException("Record not found with id or already deleted: "+recordId));
    }

    @Override
    public void restoreRecord(Long recordId) {
        financeRecordRepository.findById(recordId)
                .filter(FinanceRecord::getDeleted)
                .map(record->{
                    record.setDeleted(false);
                    return financeRecordRepository.save(record);
                }).orElseThrow(()->new RecordNotFoundException("Record not found with id or not deleted: "+recordId));
    }

    @Override
    public FinanceRecordResponse findSoftDeleted(Long softDeletedId) {
        return financeRecordRepository.findById(softDeletedId)
                .filter(FinanceRecord::getDeleted)
                .map(this::convertToRecordDto).orElseThrow(()->new RecordNotFoundException("Record not found with id or not deleted:"+softDeletedId));
    }

    @Override
    public Page<FinanceRecordResponse> findAllSoftDeleted(Pageable pageable) {
        return financeRecordRepository.findByDeletedTrue(pageable)
                .map(this::convertToRecordDto);
    }

    @Override
    public Page<FinanceRecordResponse> getRecordsByType(String type, Pageable pageable) {
        return financeRecordRepository.findByTypeIgnoreCaseAndDeletedFalse(type, pageable)
                .map(this::convertToRecordDto);
    }

    @Override
    public Page<FinanceRecordResponse> getRecordsByCategory(String category, Pageable pageable) {
        return financeRecordRepository.findByCategoryIgnoreCaseAndDeletedFalse(category, pageable)
                .map(this::convertToRecordDto);
    }

    @Override
    public Page<FinanceRecordResponse> getRecordsByDateRange(LocalDate start, LocalDate end, Pageable pageable) {
        return financeRecordRepository.findByDateBetweenAndDeletedFalse(start, end, pageable)
                .map(this::convertToRecordDto);
    }

    @Override
    public List<FinanceRecordResponse> filterRecords(String type,
                                                     String category,
                                                     LocalDate startDate,
                                                     LocalDate endDate) {

        return financeRecordRepository.findByDeletedFalse()
                .stream()
                .filter(record -> type == null || record.getType().equalsIgnoreCase(type))
                .filter(record -> category == null || record.getCategory().equalsIgnoreCase(category))
                .filter(record -> startDate == null || !record.getDate().isBefore(startDate))
                .filter(record -> endDate == null || !record.getDate().isAfter(endDate))
                .map(this::convertToRecordDto)
                .toList();
    }

    @Override
    public List<FinanceRecordResponse> searchRecords(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return financeRecordRepository.searchActiveRecords(keyword)
                .stream()
                .map(this::convertToRecordDto)
                .toList();
    }

    public FinanceRecordResponse convertToRecordDto(FinanceRecord record){
        return mapper.map(record , FinanceRecordResponse.class);
    }
}
