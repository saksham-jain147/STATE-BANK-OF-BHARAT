package com.sjain.finance.v1.bharat.utils;

import java.util.Random;

public class AccountDetailsGenerator {
    public String generateAccountNumber(){
        Random random = new Random();
        long accountNumberLong = random.nextLong() % 1000000000000L + 1000000000000L;
        return String.valueOf(accountNumberLong);
    }

    public String gereratedIFSC() {
        Random random = new Random();
        int branchCodeInt = random.nextInt(9000) + 1000;
        return String.valueOf(branchCodeInt);
    }

    public String generatePin() {
        Random random = new Random();
        int pin = random.nextInt(9000) + 1000;
        String pinString = String.valueOf(pin);
        return pinString;
    }

    public String generateBankCode() {
        Random random = new Random();
        int bankCodeInt = random.nextInt(9000) + 1000;
        return String.valueOf(bankCodeInt);
    }

    public String generateBranchCode() {
        Random random = new Random();
        int branchCodeInt = random.nextInt(9000) + 1000;
        return String.valueOf(branchCodeInt);
    }

}
