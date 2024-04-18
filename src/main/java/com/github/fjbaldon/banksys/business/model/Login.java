package com.github.fjbaldon.banksys.business.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @param passwordHash Store hashed password, not plain text
 */
public record Login(Long loginId, String username, String passwordHash, LocalDateTime createdAt,
                    LocalDateTime updatedAt, Long customerId) {

    public static class Builder {
        private Long loginId;
        private String username;
        private String passwordHash; // Enforce hashed password storage
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long customerId;

        public Builder loginId(Long loginId) {
            this.loginId = Objects.requireNonNull(loginId);
            return this;
        }

        public Builder username(String username) {
            this.username = Objects.requireNonNull(username);
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = Objects.requireNonNull(passwordHash);
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

        public Login build() {
            return new Login(loginId, username, passwordHash, createdAt, updatedAt, customerId);
        }
    }
}