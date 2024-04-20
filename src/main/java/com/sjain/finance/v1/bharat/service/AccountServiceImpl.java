package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.UserRequest;
import com.sjain.finance.v1.bharat.dto.UserResponse;
import com.sjain.finance.v1.bharat.entity.AccountInformation;
import com.sjain.finance.v1.bharat.mapper.MapperToResponse;
import com.sjain.finance.v1.bharat.repository.AccountDetailsRepository;
import com.sjain.finance.v1.bharat.utils.AccountDetailsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.sjain.finance.v1.bharat.constants.AccountDetailsConstants.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Override
    public UserResponse createAccount(UserRequest userRequest) {
        log.info("Creating a new account.");

        String userIdGenerated = UUID.randomUUID().toString();
        MapperToResponse mapperToResponse = new MapperToResponse();

        AccountDetailsGenerator accountDetailsGenerator = new AccountDetailsGenerator();

        String IFSC_CODE = accountDetailsGenerator.gereratedIFSC();
        String BANK_PIN = accountDetailsGenerator.generateBankCode();
        String PASSWORD = accountDetailsGenerator.generatePin();

        AccountInformation accountInformation = AccountInformation.builder()
                .accountId(userIdGenerated)
                .accountHolderName(userRequest.getAccountHolderName())
                .contactEmail(userRequest.getContactEmail())
                .contactPhone(userRequest.getContactPhone())
                .gender(userRequest.getGender())
                .isHaveUpiId(BANK_V3_NOTA_UPI_ID)
                .contactAddress(userRequest.getContactAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .pinCodeNumber(userRequest.getPinCodeNumber())
                .currentLocation(userRequest.getCurrentLocation())
                .designation(userRequest.getDesignation())
                .country(userRequest.getCountry())
                .password(PASSWORD)
                .bankName(BANK_V1_NAME)
                .bankBranch(BANK_V1_BRANCH)
                .routingNumber(BANK_V1_ROUTING)
                .accountType(userRequest.getAccountType())
                .status(BANK_V1_STATUS)
                .localDateTime(LocalDateTime.now())
                .accountOpenDate(LocalDate.now())
                .accountNumber(accountDetailsGenerator.generateAccountNumber())
                .ifscCode("SBB" + IFSC_CODE)
                .bankPinCode(BANK_PIN)
                .build();

        accountDetailsRepository.save(accountInformation);

        UserResponse userRes = mapperToResponse.userInformationToUserResponse(accountInformation);
        userRes.setMessage(BANK_V1_ACCOUNT_CREATED);

        log.info("Account created successfully for user: {}", userRes.getAccountHolderName());
        return userRes;
    }
}
