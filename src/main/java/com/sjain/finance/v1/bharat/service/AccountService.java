package com.sjain.finance.v1.bharat.service;

import com.sjain.finance.v1.bharat.dto.AccountUpdateDetailsResponse;
import com.sjain.finance.v1.bharat.dto.AccountUpdateDetailsRequest;
import com.sjain.finance.v1.bharat.dto.UserRequest;
import com.sjain.finance.v1.bharat.dto.UserResponse;

public interface AccountService {
    UserResponse createAccount(UserRequest userRequest);
    AccountUpdateDetailsResponse updateAccountDetails(AccountUpdateDetailsRequest accountUpdateDetailsRequest);
}
