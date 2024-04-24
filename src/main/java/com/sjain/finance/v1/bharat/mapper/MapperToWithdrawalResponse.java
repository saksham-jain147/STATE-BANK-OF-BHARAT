package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.transaction.WithdrawalResponse;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;

public class MapperToWithdrawalResponse {
    public WithdrawalResponse transactionDetailsToDepositResponse(TransactionDetails transactionDetails) {
        WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
        withdrawalResponse.setStatus("SUCCESS");
        withdrawalResponse.setTransactionReferenceId(transactionDetails.getTransactionReferenceId());
        withdrawalResponse.setAccountNumber(transactionDetails.getAccountNumber());
        withdrawalResponse.setIfscCode(transactionDetails.getIfscCode());
        withdrawalResponse.setBankName(transactionDetails.getBankName());
        withdrawalResponse.setCurrentBalance(transactionDetails.getCurrentBalance());
        withdrawalResponse.setLocalDateTime(transactionDetails.getLocalDateTime());

        return withdrawalResponse;
    }
}
