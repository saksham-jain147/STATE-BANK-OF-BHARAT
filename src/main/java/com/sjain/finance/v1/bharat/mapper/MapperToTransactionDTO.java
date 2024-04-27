package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.netBanking.TransactionDTO;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MapperToTransactionDTO {
    public List<TransactionDTO> transactionDetailsToTransactionDTO(List<TransactionDetails> transactionDetailsList){
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        for(TransactionDetails transactionDetails : transactionDetailsList){
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .date(transactionDetails.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .comment(transactionDetails.getComments())
                    .transactionId(transactionDetails.getTransactionReferenceId())
                    .closingBalance(transactionDetails.getCurrentBalance())
                    .build();
            BigDecimal amount = transactionDetails.getAmount();
            if(amount.compareTo(BigDecimal.ZERO) > 0){
                transactionDTO.setDepositAmount(amount.abs());
            } else if(amount.compareTo(BigDecimal.ZERO) < 0){
                transactionDTO.setWithdrawalAmount(amount.abs());
            }

            transactionDTOList.add(transactionDTO);
        }
        return transactionDTOList;
    }
}
