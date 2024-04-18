package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public static class Builder {
        private Long transactionId;
        private TransactionType transactionType;
        private BigDecimal amount;
        private String description;
        private LocalDateTime createdAt;
        private Long accountId;
        private Long fromAccountId;
        private Long toAccountId;

        public Builder transactionId(Long transactionId) {
            this.transactionId = Objects.requireNonNull(transactionId);
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = Objects.requireNonNull(transactionType);
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = Objects.requireNonNull(amount);
            return this;
        }

        public Builder description(String description) {
            this.description = Objects.requireNonNull(description);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt);
            return this;
        }

        public Builder accountId(Long accountId) {
            this.accountId = Objects.requireNonNull(accountId);
            return this;
        }

        public Builder fromAccountId(Long fromAccountId) {
            this.fromAccountId = Objects.requireNonNull(fromAccountId);
            return this;
        }

        public Builder toAccountId(Long toAccountId) {
            this.toAccountId = Objects.requireNonNull(toAccountId);
            return this;
        }

        public Transaction build() {
            return new Transaction(transactionId, transactionType, amount, description, createdAt, accountId, fromAccountId, toAccountId);
        }
    }
}