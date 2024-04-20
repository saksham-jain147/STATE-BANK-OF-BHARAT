package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.AccountUpdateDetailsResponse;
import com.sjain.finance.v1.bharat.dto.AccountUpdateDetailsRequest;
import com.sjain.finance.v1.bharat.dto.UserRequest;
import com.sjain.finance.v1.bharat.dto.UserResponse;
import com.sjain.finance.v1.bharat.entity.AccountInformation;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.mapper.MapperToResponse;
import com.sjain.finance.v1.bharat.mapper.MapperToUpdateResponse;
import com.sjain.finance.v1.bharat.repository.AccountDetailsRepository;
import com.sjain.finance.v1.bharat.utils.AccountDetailsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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
                .accountCreatedDateTime(LocalDateTime.now())
                .accountOpenDate(LocalDate.now())
                .accountNumber(accountDetailsGenerator.generateAccountNumber())
                .ifscCode("SBB" + IFSC_CODE)
                .bankPinCode(BANK_PIN)
                .accountLastUpdatedDateTime(LocalDateTime.now())
                .build();

        accountDetailsRepository.save(accountInformation);

        UserResponse userRes = mapperToResponse.userInformationToUserResponse(accountInformation);
        userRes.setMessage(BANK_V1_ACCOUNT_CREATED);

        log.info("Account created successfully for user: {}", userRes.getAccountHolderName());
        return userRes;
    }

    @Override
    public AccountUpdateDetailsResponse updateAccountDetails(AccountUpdateDetailsRequest accountUpdateDetailsRequest) {
        log.info("Updating account details for account number: {}", accountUpdateDetailsRequest.getAccountNumber());

        Optional<AccountInformation> userInformation = Optional.ofNullable(accountDetailsRepository.findByAccountNumber(
                accountUpdateDetailsRequest.getAccountNumber()));

        if (userInformation.isPresent()) {
            log.info("User information found for account number: {}", accountUpdateDetailsRequest.getAccountNumber());
            log.info("Updating account details for account holder: {}", accountUpdateDetailsRequest.getAccountHolderName());

            AccountInformation existingAccount = userInformation.get();

            existingAccount.setAccountHolderName(accountUpdateDetailsRequest.getAccountHolderName());
            existingAccount.setContactEmail(accountUpdateDetailsRequest.getContactEmail());
            existingAccount.setContactPhone(accountUpdateDetailsRequest.getContactPhone());
            existingAccount.setContactAddress(accountUpdateDetailsRequest.getContactAddress());
            existingAccount.setStateOfOrigin(accountUpdateDetailsRequest.getStateOfOrigin());
            existingAccount.setPinCodeNumber(accountUpdateDetailsRequest.getPinCodeNumber());
            existingAccount.setPassword(accountUpdateDetailsRequest.getPassword());
            existingAccount.setCurrentLocation(accountUpdateDetailsRequest.getCurrentLocation());
            existingAccount.setDesignation(accountUpdateDetailsRequest.getDesignation());
            existingAccount.setCountry(accountUpdateDetailsRequest.getCountry());
            existingAccount.setAccountType(accountUpdateDetailsRequest.getAccountType());
//            existingAccount.setAccountLastUpdatedDateTime(LocalDateTime.now());


            accountDetailsRepository.save(existingAccount);
            log.info("Account details updated successfully for account holder: {}", accountUpdateDetailsRequest.getAccountHolderName());

            MapperToUpdateResponse mapperToUpdateResponse = new MapperToUpdateResponse();

            AccountUpdateDetailsResponse accountUpdateDetailsResponse = mapperToUpdateResponse.userInformationToUpdateAccountResponse(existingAccount);
            accountUpdateDetailsResponse.setMessage(BANK_V1_ACCOUNT_DETAILS_UPDATED);
            return accountUpdateDetailsResponse;

        } else

            log.info("Account not found for account number: {}", accountUpdateDetailsRequest.getAccountNumber());
        throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
    }
}
