package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @param interestRate Can be null for non-interest-bearing accounts
 */
public record Account(Long accountId, String accountNumber, AccountType accountType, BigDecimal balance,
                      BigDecimal interestRate, LocalDateTime createdAt, LocalDateTime updatedAt, Long customerId) {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        LOAN
    }
}
