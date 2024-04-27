package com.sjain.finance.v1.bharat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sjain.finance.v1.bharat.dto.netBanking.StatementDTO;
import com.sjain.finance.v1.bharat.dto.netBanking.StatementRequest;
import com.sjain.finance.v1.bharat.entity.AccountInformation;
import com.sjain.finance.v1.bharat.entity.TransactionDetails;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.mapper.MapperToStatementDTO;
import com.sjain.finance.v1.bharat.repository.AccountDetailsRepository;
import com.sjain.finance.v1.bharat.repository.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NetBankingServiceImpl implements NetBankingService{
    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Autowired
    RestTemplate restTemplate;
    @Override
    public String exportStatement(StatementRequest statementRequest) {
        log.info("Exporting account statement.");
        String url = "http://localhost:9090/api/v2/exportStatement";

        Optional<AccountInformation> userInformation = Optional.ofNullable(accountDetailsRepository
                .findByAccountNumberAndPassword(statementRequest.getAccountNumber(), statementRequest.getPassword()));

        if(userInformation.isPresent()) {
            MapperToStatementDTO mapperToStatementDTO = new MapperToStatementDTO();

            AccountInformation accountInformation = userInformation.get();
//            List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findByAccountNumber(statementRequest.getAccountNumber());
            List<TransactionDetails> transactionDetailsList = transactionDetailsRepository
                    .findByLocalDateTimeBetweenAndAccountNumber(LocalDateTime.parse(statementRequest.getFromDate()+"T00:00"),
                            LocalDateTime.parse(statementRequest.getToDate()+"T00:00").plusDays(1),statementRequest.getAccountNumber());
            StatementDTO statementDTO = mapperToStatementDTO.accountDetailsTransactionDetailsToStatementDTO(accountInformation, transactionDetailsList);
            statementDTO.setFromDate(statementRequest.getFromDate());
            statementDTO.setToDate(statementRequest.getToDate());
            log.info("Success: "+ statementDTO.toString());

            return restTemplate.postForObject(url, statementDTO, String.class);
        } else

            log.info("Account not found for account number: {}", statementRequest.getAccountNumber());
        throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
    }
}
