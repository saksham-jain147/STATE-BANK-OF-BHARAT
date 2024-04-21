package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.transaction.PaymentRequest;
import com.sjain.finance.v1.bharat.dto.transaction.PaymentResponse;
import com.sjain.finance.v1.bharat.exceptions.InsufficientBalanceException;

public interface TransactionService {
    PaymentResponse makePayment(PaymentRequest paymentRequest) throws InsufficientBalanceException;
}
