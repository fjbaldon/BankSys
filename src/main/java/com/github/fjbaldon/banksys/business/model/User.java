package com.github.fjbaldon.banksys.business.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The User class represents a user in the banking system. It encapsulates
 * information such as the user's name, password, and a list of accounts
 * owned by the user. The class provides methods for retrieving user details
 * and accessing the list of owned accounts. The list of owned accounts is
 * unmodifiable externally to maintain data integrity.
 *
 * The User class follows the immutability principle by providing read-only
 * access to its internal state and using a builder-like factory method for
 * instantiation. The class promotes security by encapsulating the password,
 * and it allows association with multiple bank accounts.
 *
 * Note: Password handling in a real-world application should involve
 * additional security measures such as encryption or hashing. This example
 * simplifies the password handling for illustrative purposes.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class User {
    public static User create(String name,
                              String password) {
        return new User(name, password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getOwnedAccounts() {
        return Collections.unmodifiableList(ownedAccounts);
    }

    public void setOwnedAccounts(List<Account> ownedAccounts) {
        this.ownedAccounts = new ArrayList<>(ownedAccounts);
    }

    private final String name;
    private final String password;
    private List<Account> ownedAccounts;

    private User(String name,
                 String password) {
        this.name = name;
        this.password = password;
        this.ownedAccounts = new ArrayList<>();
    }
}
