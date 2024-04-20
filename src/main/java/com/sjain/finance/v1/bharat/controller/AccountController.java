package com.sjain.finance.v1.bharat.controller;

import com.sjain.finance.v1.bharat.dto.UserRequest;
import com.sjain.finance.v1.bharat.dto.UserResponse;
import com.sjain.finance.v1.bharat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("finance/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create-account")
    ResponseEntity<UserResponse> createYourAccount(@RequestBody UserRequest userRequest){
        UserResponse userResponse = accountService.createAccount(userRequest);
        return new ResponseEntity<UserResponse>(userResponse , HttpStatus.CREATED);
    }
}
