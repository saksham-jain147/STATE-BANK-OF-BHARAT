package com.sjain.finance.v1.bharat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "TRANSACTION_HISTORY")
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String transactionReferenceId;
    @Column(nullable = false)
    @JoinColumn(name = "account_number", referencedColumnName = "accountNumber")
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime localDateTime;
    @JoinColumn(name = "counterparty_account_number", referencedColumnName = "accountNumber")
    private String counterpartyAccountNumber;
    private String comments;
    private BigDecimal currentBalance;
}
