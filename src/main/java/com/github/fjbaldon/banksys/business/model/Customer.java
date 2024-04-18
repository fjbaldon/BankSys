package com.github.fjbaldon.banksys.business.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public record Customer(Long customerId, String firstName, String lastName, String middleInitial, LocalDate dateOfBirth,
                       String email, String phoneNumber, String address, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {

    public static class Builder {
        private Long customerId;
        private String firstName;
        private String lastName;
        private String middleInitial;
        private LocalDate dateOfBirth;
        private String email;
        private String phoneNumber;
        private String address;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder customerId(Long customerId) {
            this.customerId = Objects.requireNonNull(customerId);
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = Objects.requireNonNull(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = Objects.requireNonNull(lastName);
            return this;
        }

        public Builder middleInitial(String middleInitial) {
            this.middleInitial = Objects.requireNonNull(middleInitial);
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = Objects.requireNonNull(dateOfBirth);
            return this;
        }

        public Builder email(String email) {
            this.email = Objects.requireNonNull(email);
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = Objects.requireNonNull(phoneNumber);
            return this;
        }

        public Builder address(String address) {
            this.address = Objects.requireNonNull(address);
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

        public Customer build() {
            return new Customer(customerId, firstName, lastName, middleInitial, dateOfBirth, email, phoneNumber, address, createdAt, updatedAt);
        }
    }
}