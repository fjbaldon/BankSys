package com.github.fjbaldon.banksys.business.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Login {

    public Long getLoginId() {
        return loginId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(loginId, login.loginId) && Objects.equals(username, login.username) && Objects.equals(passwordHash, login.passwordHash) && Objects.equals(createdAt, login.createdAt) && Objects.equals(updatedAt, login.updatedAt) && Objects.equals(customerId, login.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, username, passwordHash, createdAt, updatedAt, customerId);
    }

    @Override
    public String toString() {
        return "Login{" +
                "loginId=" + loginId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", customerId=" + customerId +
                '}';
    }

    private Long loginId;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long customerId;

    /**
     * @param passwordHash Store hashed password, not plain text
     */
    public Login(Long loginId, String username, String passwordHash, LocalDateTime createdAt,
                 LocalDateTime updatedAt, Long customerId) {
        this.loginId = loginId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerId = customerId;
    }
}