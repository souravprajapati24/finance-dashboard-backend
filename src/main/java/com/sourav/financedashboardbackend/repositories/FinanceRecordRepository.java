package com.sourav.financedashboardbackend.repositories;

import com.sourav.financedashboardbackend.model.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRecordRepository extends JpaRepository<FinanceRecord ,Long> {

}
