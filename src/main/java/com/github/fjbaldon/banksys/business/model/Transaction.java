package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @param fromAccountId Can be null for non-transfer transactions
 * @param toAccountId   Can be null for non-transfer transactions
 */
public record Transaction(Long transactionId, TransactionType transactionType, BigDecimal amount, String description,
                          LocalDateTime createdAt, Long accountId, Long fromAccountId, Long toAccountId) {

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT,
        FEE
    }
}