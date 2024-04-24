package com.sjain.finance.v1.bharat.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private String status;
    private String transactionReferenceId;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private BigDecimal currentBalance;
    private LocalDateTime localDateTime;
}
