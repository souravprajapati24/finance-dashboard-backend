package com.sourav.financedashboardbackend.repositories;

import com.sourav.financedashboardbackend.model.FinanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FinanceRecordRepository extends JpaRepository<FinanceRecord ,Long> {

    Page<FinanceRecord> findByTypeIgnoreCaseAndDeletedFalse(String type, Pageable pageable);
    Page<FinanceRecord> findByCategoryIgnoreCaseAndDeletedFalse(String category, Pageable pageable);
    Page<FinanceRecord> findByDateBetweenAndDeletedFalse(LocalDate start, LocalDate end, Pageable pageable);

    List<FinanceRecord> findByDeletedFalse();
    Page<FinanceRecord> findByDeletedFalse(Pageable pageable);
    @Query("""
    SELECT f FROM FinanceRecord f
    WHERE f.deleted = false AND (
        LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(f.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(f.type) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
""")
    List<FinanceRecord> searchActiveRecords(@Param("keyword") String keyword);

    Page<FinanceRecord> findByDeletedTrue(Pageable pageable);

    @Query("SELECT f.category AS category, SUM(f.amount) AS total " +
            "FROM FinanceRecord f " +
            "WHERE f.deleted = false " +
            "GROUP BY f.category")
    List<Object[]> findCategoryTotals();
    List<FinanceRecord> findAllByDeletedFalse();




}
