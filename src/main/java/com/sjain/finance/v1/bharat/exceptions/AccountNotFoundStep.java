package com.sjain.finance.v1.bharat.exceptions;

public class AccountNotFoundStep extends RuntimeException{
    public AccountNotFoundStep(String message){
        super(message);
    }
}
