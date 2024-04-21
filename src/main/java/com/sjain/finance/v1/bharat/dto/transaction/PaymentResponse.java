package com.sjain.finance.v1.bharat.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String transactionReferenceId;
    private String transactionType;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String counterpartyAccountNumber;
    private BigDecimal amount;
    private BigDecimal currentBalance;
    private LocalDateTime localDateTime;
}
