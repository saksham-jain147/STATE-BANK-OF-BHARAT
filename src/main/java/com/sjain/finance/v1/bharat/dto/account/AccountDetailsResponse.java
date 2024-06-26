package com.sjain.finance.v1.bharat.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsResponse {
    private String accountId;
    private String accountHolderName;
    private String contactEmail;
    private String contactPhone;
    private String gender;
    private String contactAddress;
    private String stateOfOrigin;
    private String pinCodeNumber;
    private String currentLocation;
    private String designation;
    private String country;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String bankBranch;
    private String routingNumber;
    private String accountType;
    private BigDecimal accountBalance;
    private String status;
    private LocalDate accountOpenDate;
    private LocalDateTime accountCreatedDateTime;
    private LocalDateTime accountLastUpdatedDateTime;
}
