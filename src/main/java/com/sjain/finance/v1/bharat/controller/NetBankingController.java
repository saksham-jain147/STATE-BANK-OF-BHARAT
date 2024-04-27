package com.sjain.finance.v1.bharat.controller;

import com.sjain.finance.v1.bharat.dto.netBanking.StatementRequest;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.service.NetBankingService;
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
@RequestMapping("finance/v1/net-banking")
public class NetBankingController {
    @Autowired
    NetBankingService netBankingService;
    @PostMapping("/export-statement")
    ResponseEntity<String> exportStatement(@RequestBody StatementRequest statementRequest){
        try {
            String response = netBankingService.exportStatement(statementRequest);
//            return new ResponseEntity<StatementResponse>(response, HttpStatus.ACCEPTED);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountNotFoundStep e){
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
