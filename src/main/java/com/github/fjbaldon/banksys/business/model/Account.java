package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Account implements Model {

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) && Objects.equals(accountNumber, account.accountNumber) && accountType == account.accountType && Objects.equals(balance, account.balance) && Objects.equals(interestRate, account.interestRate) && Objects.equals(createdAt, account.createdAt) && Objects.equals(updatedAt, account.updatedAt) && Objects.equals(customerId, account.customerId) && Objects.equals(isDeleted, account.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountNumber, accountType, balance, interestRate, createdAt, updatedAt, customerId, isDeleted);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", customerId=" + customerId +
                ", isDeleted=" + isDeleted +
                '}';
    }

    private final Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long customerId;
    private Boolean isDeleted;

    /**
     * @param interestRate Can be null for non-interest-bearing accounts
     */
    public Account(Long accountId, String accountNumber, AccountType accountType, BigDecimal balance,
                   BigDecimal interestRate, LocalDateTime createdAt, LocalDateTime updatedAt, Long customerId,
                   Boolean isDeleted) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.interestRate = interestRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerId = customerId;
        this.isDeleted = isDeleted;
    }

    public enum AccountType {
        Checking,
        Savings,
        Loan
    }
}