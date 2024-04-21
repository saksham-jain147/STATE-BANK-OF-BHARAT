package com.sjain.finance.v1.bharat.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String senderAccountNumber;
    private String senderAccountPassword;
    private String senderBankName;
    private String senderBankIfscCode;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String comments;
}
