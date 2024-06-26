package com.sjain.finance.v1.bharat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "USER_INFO")
public class AccountInformation {

    @Id
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
    private String password;
    private String ifscCode;
    private String bankName;
    private String bankBranch;
    private String routingNumber;
    private String bankPinCode;
    private String accountType;
    private String isHaveUpiId;
    private BigDecimal accountBalance;
    private String status;
    private LocalDate accountOpenDate;
    private LocalDateTime accountCreatedDateTime;
    private LocalDateTime accountLastUpdatedDateTime;


}
