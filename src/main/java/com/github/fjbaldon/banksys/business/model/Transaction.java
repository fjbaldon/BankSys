package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Transaction implements Model {

    public Long getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && transactionType == that.transactionType && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(createdAt, that.createdAt) && Objects.equals(accountId, that.accountId) && Objects.equals(fromAccountId, that.fromAccountId) && Objects.equals(toAccountId, that.toAccountId) && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionType, amount, description, createdAt, accountId, fromAccountId, toAccountId, isDeleted);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", accountId=" + accountId +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", isDeleted=" + isDeleted +
                '}';
    }

    private final Long transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private Long accountId;
    private Long fromAccountId;
    private Long toAccountId;
    private Boolean isDeleted;

    /**
     * @param fromAccountId Can be null for non-transfer transactions
     * @param toAccountId   Can be null for non-transfer transactions
     */
    public Transaction(Long transactionId, TransactionType transactionType, BigDecimal amount, String description,
                       LocalDateTime createdAt, Long accountId, Long fromAccountId, Long toAccountId, Boolean isDeleted) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.accountId = accountId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.isDeleted = isDeleted;
    }

    public enum TransactionType {
        Deposit,
        Withdrawal,
        Transfer,
        Payment,
        Fee
    }
}