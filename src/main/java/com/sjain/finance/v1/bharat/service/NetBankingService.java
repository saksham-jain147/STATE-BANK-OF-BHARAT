package com.sjain.finance.v1.bharat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sjain.finance.v1.bharat.dto.netBanking.StatementRequest;

public interface NetBankingService {
    String exportStatement(StatementRequest statementRequest);
}
