package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.transaction.PaymentResponse;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;

public class MapperToPaymentResponse {
    public PaymentResponse transactionDetailsToPaymentResponse(TransactionDetails transactionDetails){
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionReferenceId(transactionDetails.getTransactionReferenceId());
        paymentResponse.setTransactionType(transactionDetails.getTransactionType());
        paymentResponse.setAccountNumber(transactionDetails.getAccountNumber());
        paymentResponse.setCounterpartyAccountNumber(transactionDetails.getCounterpartyAccountNumber());
        paymentResponse.setIfscCode(transactionDetails.getIfscCode());
        paymentResponse.setBankName(transactionDetails.getBankName());
        paymentResponse.setAmount(transactionDetails.getAmount().abs());
        paymentResponse.setCurrentBalance(transactionDetails.getCurrentBalance());
        paymentResponse.setLocalDateTime(transactionDetails.getLocalDateTime());

        return paymentResponse;
    }
}
