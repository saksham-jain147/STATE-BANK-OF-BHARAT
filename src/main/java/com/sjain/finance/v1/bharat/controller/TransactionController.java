package com.sjain.finance.v1.bharat.controller;

import com.sjain.finance.v1.bharat.dto.transaction.PaymentRequest;
import com.sjain.finance.v1.bharat.dto.transaction.PaymentResponse;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.exceptions.InsufficientBalanceException;
import com.sjain.finance.v1.bharat.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("finance/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/make-payment")
    ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest){
        try {
            PaymentResponse response = transactionService.makePayment(paymentRequest);
            return new ResponseEntity<PaymentResponse>(response, HttpStatus.ACCEPTED);
        } catch (InsufficientBalanceException e){
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
        } catch (AccountNotFoundStep e){
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
