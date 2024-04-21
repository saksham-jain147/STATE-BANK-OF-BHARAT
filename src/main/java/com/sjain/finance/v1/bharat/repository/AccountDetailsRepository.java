package com.sjain.finance.v1.bharat.repository;

import com.sjain.finance.v1.bharat.entity.AccountInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountInformation, String> {
    @Query("SELECT u FROM AccountInformation u WHERE u.contactEmail = :contactEmail")
    AccountInformation findByContactEmail(@Param("contactEmail") String contactEmail);

    @Query("SELECT u FROM AccountInformation u WHERE u.contactPhone = :contactPhone")
    AccountInformation findByContactPhoneNumber(@Param("contactPhone") String phoneNumber);

    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber")
    AccountInformation findByAccountNumber(String accountNumber);

    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber AND u.ifscCode = :ifscCode")
    AccountInformation findByAccountIdAndIfscCode(String accountNumber, String ifscCode);

    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber AND u.ifscCode = :ifscCode AND u.password = :password")
    AccountInformation findByAccountIdIfscCodeAndPassword(String accountNumber, String ifscCode , String password);
    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber AND u.contactEmail = :contactEmail AND u.password = :password")
    AccountInformation findByAccountIdContactEmailAndPassword(String accountNumber, String contactEmail , String password);

    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber AND u.password = :password")
    AccountInformation findByAccountNumberAndPassword(String accountNumber , String password);

    @Query("SELECT u FROM AccountInformation u WHERE u.accountNumber = :accountNumber AND u.accountHolderName = :accountHolderName")
    AccountInformation findByAccountNumberAndName(String accountNumber, String accountHolderName);
}
