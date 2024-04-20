package com.sjain.finance.v1.bharat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDeleteDetailsRequest {
    private String accountHolderName;
    private String password;
    private String accountNumber;
    private String contactEmail;
    private String contactPhone;
}
