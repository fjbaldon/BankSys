package com.github.fjbaldon.banksys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Transaction class represents a financial transaction within the banking system.
 * It encapsulates details such as transaction ID, date, description, amount,
 * and the associated bank account. The class provides methods for retrieving
 * transaction details, including getters for ID, date, description, amount,
 * and the associated account.
 *
 * The Transaction class follows the immutability principle by providing read-only
 * access to its internal state and using a factory method for instantiation.
 * Each transaction is associated with a unique ID, and if the ID is set to the
 * special value NEW_ID, a unique ID is generated using an AtomicInteger.
 *
 * Note: In a real-world scenario, transactions might involve more complex
 * logic and additional attributes. This example simplifies the transaction
 * model for illustrative purposes.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class Transaction {
    public static final int NEW_ID = 0;

    public static Transaction create(int id,
                                     LocalDateTime date,
                                     String description,
                                     BigDecimal amount,
                                     Account account) {
        return new Transaction(id, date, description, amount, account);
    }

    private static final AtomicInteger nextID = new AtomicInteger((int) (Math.random() * Integer.MAX_VALUE));

    public int getID() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    private final int id;
    private final LocalDateTime date;
    private final String description;
    private final BigDecimal amount;
    private final Account account;

    private Transaction(int id,
                        LocalDateTime date,
                        String description,
                        BigDecimal amount,
                        Account account) {
        if (id == NEW_ID)
            this.id = nextID.getAndIncrement();
        else
            this.id = id;

        this.date = date;
        this.description = description;
        this.amount = amount;
        this.account = account;
    }
}
