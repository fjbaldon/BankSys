package com.github.fjbaldon.banksys.business.model;

import java.time.LocalDateTime;

/**
 * @param passwordHash Store hashed password, not plain text
 */
public record Login(Long loginId, String username, String passwordHash, LocalDateTime createdAt,
                    LocalDateTime updatedAt, Long customerId) {
}
