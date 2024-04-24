package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.transaction.*;
import com.sjain.finance.v1.bharat.exceptions.InsufficientBalanceException;

public interface TransactionService {
    PaymentResponse makePayment(PaymentRequest paymentRequest) throws InsufficientBalanceException;

    DepositResponse depositCash(DepositRequest depositRequest);

    WithdrawalResponse withdrawCash(WithdrawalRequest withdrawalRequest) throws InsufficientBalanceException;
}
