package com.sjain.finance.v1.bharat.mapper;

import com.sjain.finance.v1.bharat.dto.account.AccountUpdateDetailsResponse;
import com.sjain.finance.v1.bharat.entity.AccountInformation;

public class MapperToUpdateResponse {
    public AccountUpdateDetailsResponse userInformationToUpdateAccountResponse(AccountInformation accountInformation) {

        AccountUpdateDetailsResponse updateMapper = new AccountUpdateDetailsResponse();
        updateMapper.setAccountHolderName(accountInformation.getAccountHolderName());
        updateMapper.setAccountType(accountInformation.getAccountType());
        updateMapper.setContactEmail(accountInformation.getContactEmail());
        updateMapper.setContactPhone(accountInformation.getContactPhone());
        updateMapper.setContactAddress(accountInformation.getContactAddress());
        updateMapper.setStateOfOrigin(accountInformation.getStateOfOrigin());
        updateMapper.setPinCodeNumber(accountInformation.getPinCodeNumber());
        updateMapper.setCurrentLocation(accountInformation.getCurrentLocation());
        updateMapper.setDesignation(accountInformation.getDesignation());
        updateMapper.setCountry(accountInformation.getCountry());
        updateMapper.setAccountType(updateMapper.getAccountType());
        updateMapper.setAccountCreatedDateTime(accountInformation.getAccountCreatedDateTime());
        updateMapper.setAccountLastUpdateDateTime(accountInformation.getAccountLastUpdatedDateTime());

        return updateMapper;
    }
}
