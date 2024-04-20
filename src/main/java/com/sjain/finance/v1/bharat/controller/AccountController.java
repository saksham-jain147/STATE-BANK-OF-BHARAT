package com.sjain.finance.v1.bharat.controller;

import com.sjain.finance.v1.bharat.dto.*;
import com.sjain.finance.v1.bharat.exceptions.AccountNotFoundStep;
import com.sjain.finance.v1.bharat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("finance/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create-account")
    ResponseEntity<UserResponse> createNewAccount(@RequestBody UserRequest userRequest){
        UserResponse userResponse = accountService.createAccount(userRequest);
        return new ResponseEntity<UserResponse>(userResponse , HttpStatus.CREATED);
    }

    @PutMapping("/update-account-details")
    ResponseEntity<AccountUpdateDetailsResponse> updateAnAccountDetails(@RequestBody AccountUpdateDetailsRequest accountDetailsRequest){
        AccountUpdateDetailsResponse accountUpdateDetailsResponse = null;
        try {
            accountUpdateDetailsResponse = accountService.updateAccountDetails(accountDetailsRequest);
        } catch (AccountNotFoundStep e) {
//            accountUpdateDetailsResponse = new AccountUpdateDetailsResponse();
//            accountUpdateDetailsResponse.setMessage(BANK_V1_ACCOUNT_NOT_EXISTS);
//            return new ResponseEntity<AccountUpdateDetailsResponse>(accountUpdateDetailsResponse, HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<AccountUpdateDetailsResponse>(accountUpdateDetailsResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-account-details/{accountNumber}/{IFSCCode}/{password}")
    ResponseEntity<AccountDetailsResponse> getAccountDetails(@PathVariable String accountNumber,
                                                             @PathVariable String IFSCCode,
                                                             @PathVariable String password){
        AccountDetailsResponse accountDetailsResponse = null;
        try {
             accountDetailsResponse = accountService.getYourAccountDetails(accountNumber, IFSCCode, password);
        } catch (AccountNotFoundStep e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<AccountDetailsResponse>(accountDetailsResponse, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete-account")
    ResponseEntity<AccountDeletedSuccessResponse> deleteAccount(@RequestBody AccountDeleteDetailsRequest accountDetailsRequest){
        AccountDeletedSuccessResponse accountDeletedSuccessResponse = null;
        try {
            accountDeletedSuccessResponse = accountService.deleteAccount(accountDetailsRequest);
        } catch (AccountNotFoundStep e){
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Message",String.valueOf(e));
            return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<AccountDeletedSuccessResponse>(accountDeletedSuccessResponse, HttpStatus.ACCEPTED);
    }
}
