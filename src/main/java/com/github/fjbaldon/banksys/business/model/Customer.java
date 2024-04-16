package com.github.fjbaldon.banksys.business.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Customer(Long customerId, String firstName, String lastName, String middleInitial, LocalDate dateOfBirth,
                       String ssnHashed, String email, String phoneNumber, String address, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
}
