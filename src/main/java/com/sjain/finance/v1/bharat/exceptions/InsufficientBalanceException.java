package com.sjain.finance.v1.bharat.exceptions;

public class InsufficientBalanceException extends  Exception{
    public InsufficientBalanceException(String message){
        super(message);
    }
}
