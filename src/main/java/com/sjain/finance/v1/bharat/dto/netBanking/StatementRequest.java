package com.sjain.finance.v1.bharat.dto.netBanking;

import lombok.Data;

@Data
public class StatementRequest {
    private String accountNumber;
    private String password;
    private String fromDate;
    private String toDate;

}
