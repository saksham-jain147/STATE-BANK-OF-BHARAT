package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.account.*;

public interface AccountService {
    UserResponse createAccount(UserRequest userRequest);
    AccountUpdateDetailsResponse updateAccountDetails(AccountUpdateDetailsRequest accountUpdateDetailsRequest);
    AccountDetailsResponse getYourAccountDetails(String accountNumber, String ifscCode, String password);
    AccountDeletedSuccessResponse deleteAccount(AccountDeleteDetailsRequest accountDetailsRequest);
}
