package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public static class Builder {
        private Long accountId;
        private String accountNumber;
        private AccountType accountType;
        private BigDecimal balance;
        private BigDecimal interestRate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long customerId;

        public Builder accountId(Long accountId) {
            this.accountId = Objects.requireNonNull(accountId);
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = Objects.requireNonNull(accountNumber);
            return this;
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = Objects.requireNonNull(accountType);
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = Objects.requireNonNull(balance);
            return this;
        }

        public Builder interestRate(BigDecimal interestRate) {
            this.interestRate = Objects.requireNonNull(interestRate);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = Objects.requireNonNull(updatedAt);
            return this;
        }

        public Builder customerId(Long customerId) {
            this.customerId = Objects.requireNonNull(customerId);
            return this;
        }

        public Account build() {
            return new Account(accountId, accountNumber, accountType, balance, interestRate, createdAt, updatedAt, customerId);
        }
    }
}