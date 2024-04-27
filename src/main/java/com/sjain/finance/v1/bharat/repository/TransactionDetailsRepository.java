package com.sjain.finance.v1.bharat.repository;

import com.sjain.finance.v1.bharat.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByAccountNumber(String accountNumber);
    List<TransactionDetails> findByLocalDateTimeBetweenAndAccountNumber(LocalDateTime startDate, LocalDateTime endDate, String accountNumber);

}
