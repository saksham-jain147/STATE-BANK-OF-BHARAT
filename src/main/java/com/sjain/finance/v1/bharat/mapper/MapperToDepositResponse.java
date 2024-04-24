package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.transaction.DepositResponse;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;

public class MapperToDepositResponse {
    public DepositResponse transactionDetailsToDepositResponse(TransactionDetails transactionDetails) {
        DepositResponse depositResponse = new DepositResponse();
        depositResponse.setStatus("SUCCESS");
        depositResponse.setTransactionReferenceId(transactionDetails.getTransactionReferenceId());
        depositResponse.setAccountNumber(transactionDetails.getAccountNumber());
        depositResponse.setIfscCode(transactionDetails.getIfscCode());
        depositResponse.setBankName(transactionDetails.getBankName());
        depositResponse.setCurrentBalance(transactionDetails.getCurrentBalance());
        depositResponse.setLocalDateTime(transactionDetails.getLocalDateTime());

        return depositResponse;
    }
}
