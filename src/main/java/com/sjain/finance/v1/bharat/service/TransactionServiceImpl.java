package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.transaction.DepositRequest;
import com.sjain.finance.v1.bharat.dto.transaction.DepositResponse;
import com.sjain.finance.v1.bharat.dto.transaction.PaymentRequest;
import com.sjain.finance.v1.bharat.dto.transaction.PaymentResponse;
import com.sjain.finance.v1.bharat.entity.AccountInformation;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.exceptions.InsufficientBalanceException;
import com.sjain.finance.v1.bharat.mapper.MapperToDepositResponse;
import com.sjain.finance.v1.bharat.mapper.MapperToPaymentResponse;
import com.sjain.finance.v1.bharat.repository.AccountDetailsRepository;
import com.sjain.finance.v1.bharat.repository.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;
    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws InsufficientBalanceException {
        log.info("Crediting money for account number: {}", paymentRequest.getReceiverAccountNumber());
        AccountInformation receiverAccountInformation = accountDetailsRepository.findByAccountNumber(paymentRequest.getReceiverAccountNumber());
        AccountInformation senderAccountInformation = accountDetailsRepository.findByAccountIdIfscCodeAndPassword(paymentRequest.getSenderAccountNumber(), paymentRequest.getSenderBankIfscCode(), paymentRequest.getSenderAccountPassword());

        if(receiverAccountInformation != null && senderAccountInformation != null){
            log.info("Accounts found for both receiver and sender. Receiver Account Number: {}, Sender Account Number: {}", paymentRequest.getReceiverAccountNumber(), paymentRequest.getSenderAccountNumber());

            MapperToPaymentResponse mapperToPaymentResponse = new MapperToPaymentResponse();

            BigDecimal amount = paymentRequest.getAmount();

            BigDecimal receiverBalance = receiverAccountInformation.getAccountBalance();
            BigDecimal senderBalance = senderAccountInformation.getAccountBalance();

            if(senderBalance.compareTo(amount)<0){
                log.info("Insufficient balance in sender's account. Cannot process this transaction.");
                throw new InsufficientBalanceException("Insufficient balance in sender's account. Cannot process this transaction.");
            }

            senderAccountInformation.setAccountBalance(senderBalance.subtract(amount));
            receiverAccountInformation.setAccountBalance(receiverBalance.add(amount));

            senderAccountInformation = accountDetailsRepository.save(senderAccountInformation);
            receiverAccountInformation = accountDetailsRepository.save(receiverAccountInformation);

            String transactionReferenceId = UUID.randomUUID().toString();

            TransactionDetails debitTransactionDetails = TransactionDetails.builder()
                    .transactionReferenceId(transactionReferenceId)
                    .accountNumber(senderAccountInformation.getAccountNumber())
                    .bankName(senderAccountInformation.getBankName())
                    .ifscCode(senderAccountInformation.getIfscCode())
                    .transactionType("DEBIT")
                    .amount(amount.negate())
                    .localDateTime(LocalDateTime.now())
                    .counterpartyAccountNumber(receiverAccountInformation.getAccountNumber())
                    .comments(paymentRequest.getComments())
                    .currentBalance(senderAccountInformation.getAccountBalance())
                    .build();

            transactionDetailsRepository.save(debitTransactionDetails);

            TransactionDetails creditTransactionDetails = TransactionDetails.builder()
                    .transactionReferenceId(transactionReferenceId)
                    .accountNumber(receiverAccountInformation.getAccountNumber())
                    .bankName(receiverAccountInformation.getBankName())
                    .ifscCode(receiverAccountInformation.getIfscCode())
                    .transactionType("CREDIT")
                    .amount(amount)
                    .localDateTime(LocalDateTime.now())
                    .counterpartyAccountNumber(senderAccountInformation.getAccountNumber())
                    .comments(paymentRequest.getComments())
                    .currentBalance(receiverAccountInformation.getAccountBalance())
                    .build();

            transactionDetailsRepository.save(creditTransactionDetails);

            log.info("Money debited successfully and credited to account number: {}. Current balance: {}", paymentRequest.getReceiverAccountNumber(), receiverAccountInformation.getAccountBalance());

            PaymentResponse paymentResponse = mapperToPaymentResponse.transactionDetailsToPaymentResponse(debitTransactionDetails);
            return paymentResponse;
        } else if(receiverAccountInformation == null && senderAccountInformation != null){
            log.info("Account not found for receiving money. Account details: AccountNumber={}", paymentRequest.getReceiverAccountNumber());
            throw new AccountNotFoundStep("The details you have entered for receiver's account are incorrect. There is no account with these details. Please double-check the information and try again.");
        } else if(receiverAccountInformation != null && senderAccountInformation == null){
            log.info("Account not found for crediting money. Account details: AccountNumber={}, IFSCCode={}, Password={}",
                    paymentRequest.getSenderAccountNumber(), paymentRequest.getSenderBankIfscCode(), paymentRequest.getSenderAccountPassword());
            throw new AccountNotFoundStep("The details you have entered for sender's account are incorrect. There is no account with these details. Please double-check the information and try again.");
        } else {
            log.info("Account not found for both sender and receiver.");
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }

    }

    @Override
    @Transactional
    public DepositResponse depositCash(DepositRequest depositRequest) {
        log.info("Adding money for account number: {}", depositRequest.getAccountNumber());
        AccountInformation accountInformation = accountDetailsRepository.findByAccountIdIfscCodeAndPassword(depositRequest.getAccountNumber(), depositRequest.getIfscCode(), depositRequest.getPassword());

        if(accountInformation != null){
            log.info("Account found for Account Number : {}", depositRequest.getAccountNumber());

            MapperToDepositResponse mapperToDepositResponse = new MapperToDepositResponse();

            BigDecimal amount = depositRequest.getAmount();
            BigDecimal currentBalance = accountInformation.getAccountBalance();

            accountInformation.setAccountBalance(currentBalance.add(amount));
            accountInformation = accountDetailsRepository.save(accountInformation);

            String transactionReferenceId = UUID.randomUUID().toString();

            TransactionDetails transactionDetails = TransactionDetails.builder()
                    .transactionReferenceId(transactionReferenceId)
                    .accountNumber(accountInformation.getAccountNumber())
                    .bankName(accountInformation.getBankName())
                    .ifscCode(accountInformation.getIfscCode())
                    .transactionType("CREDIT")
                    .amount(amount)
                    .localDateTime(LocalDateTime.now())
                    .comments(depositRequest.getComments())
                    .currentBalance(accountInformation.getAccountBalance())
                    .build();

            transactionDetailsRepository.save(transactionDetails);

            log.info("Money successfully deposited to account number : {}", accountInformation.getAccountNumber());

            DepositResponse depositResponse = mapperToDepositResponse.transactionDetailsToDepositResponse(transactionDetails);
            return depositResponse;
        } else {
            log.info("Account not found for Account Number : {}", depositRequest.getAccountNumber());
            throw new AccountNotFoundStep("The details you have entered for sender's account are incorrect. There is no account with these details. Please double-check the information and try again.");
        }
    }
}
