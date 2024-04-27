package com.sjain.finance.v1.bharat.dto.netBanking;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementDTO {
    private String prefix;
    private String accountHolderName;
    private String contactAddress;
    private String stateOfOrigin;
    private String country;
    private String bankBranch;
    private String routingNumber;
    private String currency;
    private String contactEmail;
    private String accountNumber;
    private String accountOpenDate;
    private String accountType;
    private String ifscCode;
    private String branchCode;
    private String fromDate;
    private String toDate;
    private List<TransactionDTO> transactions;
    private BigDecimal openingBalance;
    private String debitCount;
    private String creditCount;
    private BigDecimal totalDebits;
    private BigDecimal totalCredits;
    private BigDecimal closingBalance;
    private String generatedOn;
}
