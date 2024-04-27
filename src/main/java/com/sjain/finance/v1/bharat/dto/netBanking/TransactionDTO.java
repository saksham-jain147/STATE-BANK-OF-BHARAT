package com.sjain.finance.v1.bharat.dto.netBanking;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class TransactionDTO {
    private String date;
    private String comment;
    private String transactionId;
    private BigDecimal withdrawalAmount;
    private BigDecimal depositAmount;
    private BigDecimal closingBalance;
}
