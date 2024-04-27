package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.netBanking.StatementDTO;
import com.sjain.finance.v1.bharat.dto.netBanking.TransactionDTO;
import com.sjain.finance.v1.bharat.entity.AccountInformation;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MapperToStatementDTO {

    public StatementDTO accountDetailsTransactionDetailsToStatementDTO(AccountInformation accountInformation, List<TransactionDetails> transactionDetailsList) {
        MapperToTransactionDTO mapperToTransactionDTO = new MapperToTransactionDTO();
        List<TransactionDTO> transactionDTOList = mapperToTransactionDTO.transactionDetailsToTransactionDTO(transactionDetailsList);

        StatementDTO statementDTO = StatementDTO.builder()
                .accountHolderName(accountInformation.getAccountHolderName().toUpperCase())
                .contactAddress(accountInformation.getContactAddress().toUpperCase())
                .stateOfOrigin(accountInformation.getStateOfOrigin().toUpperCase())
                .country(accountInformation.getCountry().toUpperCase())
                .bankBranch(accountInformation.getBankBranch())
                .routingNumber(accountInformation.getRoutingNumber())
                .currency("INR")
                .contactEmail(accountInformation.getContactEmail())
                .accountNumber(accountInformation.getAccountNumber())
                .accountOpenDate(accountInformation.getAccountOpenDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .accountType(accountInformation.getAccountType())
                .ifscCode(accountInformation.getIfscCode())
                .branchCode(accountInformation.getBankPinCode())
                .transactions(transactionDTOList)
                .generatedOn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

        statementDTO.setPrefix(prefix(accountInformation.getGender()));
        statementDTO.setOpeningBalance(openingBalance(transactionDTOList));
        statementDTO.setDebitCount(debitCount(transactionDTOList));
        statementDTO.setCreditCount(creditCount(transactionDTOList));
        statementDTO.setTotalDebits(totalDebits(transactionDTOList));
        statementDTO.setTotalCredits(totalCredits(transactionDTOList));
        statementDTO.setClosingBalance(closingBalance(transactionDTOList));

        return statementDTO;
    }

    private BigDecimal closingBalance(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return BigDecimal.ZERO;

        TransactionDTO transactionDTO = transactionDTOList.get(transactionDTOList.size()-1);

        return transactionDTO.getClosingBalance();
    }

    private BigDecimal totalCredits(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return BigDecimal.ZERO;

        BigDecimal amount = BigDecimal.ZERO;
        for(TransactionDTO transactionDTO : transactionDTOList){
            if(transactionDTO.getDepositAmount() != null){
                amount = amount.add(transactionDTO.getDepositAmount());
            }
        }
        return amount;
    }

    private BigDecimal totalDebits(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return BigDecimal.ZERO;

        BigDecimal amount = BigDecimal.ZERO;
        for(TransactionDTO transactionDTO : transactionDTOList){
            if(transactionDTO.getWithdrawalAmount() != null){
                amount = amount.add(transactionDTO.getWithdrawalAmount());
            }
        }
        return amount;
    }

    private String debitCount(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return "0.00";

        int count=0;
        for(TransactionDTO transactionDTO : transactionDTOList){
            if(transactionDTO.getWithdrawalAmount() != null){
                count++;
            }
        }
        return Integer.toString(count);
    }
    private String creditCount(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return "0.00";

        int count=0;
        for(TransactionDTO transactionDTO : transactionDTOList){
            if(transactionDTO.getDepositAmount() != null){
                count++;
            }
        }
        return Integer.toString(count);
    }

    private BigDecimal openingBalance(List<TransactionDTO> transactionDTOList) {
        if(transactionDTOList.size()==0) return BigDecimal.ZERO;

        TransactionDTO transactionDTO = transactionDTOList.get(0);
        BigDecimal openingBalance = transactionDTO.getClosingBalance();
        if(transactionDTO.getWithdrawalAmount() != null){
            openingBalance = openingBalance.add(transactionDTO.getWithdrawalAmount());
        } else if(transactionDTO.getDepositAmount() != null){
            openingBalance = openingBalance.subtract(transactionDTO.getDepositAmount());
        }
        return openingBalance;
    }

    private String prefix(String gender){
        return gender.toLowerCase().equals("male") ? "MR" : "MS";
    }
}
