package com.sjain.finance.v1.bharat.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequest {
    private String accountNumber;
    private  String password;
    private String bankPinCode;
    private String ifscCode;
    private String bankName;
    private BigDecimal amount;
    private String comments;
}
