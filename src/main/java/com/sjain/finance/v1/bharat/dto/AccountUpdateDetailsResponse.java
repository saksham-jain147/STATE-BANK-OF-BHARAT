package com.sjain.finance.v1.bharat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDetailsResponse {
    private String message;
    private String accountHolderName;
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;
    private String stateOfOrigin;
    private String pinCodeNumber;
    private String currentLocation;
    private String designation;
    private String country;
    private String accountType;
    private LocalDateTime accountCreatedDateTime;
    private LocalDateTime accountLastUpdateDateTime;
}
