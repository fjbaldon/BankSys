package com.github.fjbaldon.banksys.controller;

import com.github.fjbaldon.banksys.BankSys;
import com.github.fjbaldon.banksys.database.Database;
import com.github.fjbaldon.banksys.model.Account;
import com.github.fjbaldon.banksys.model.User;
import com.github.fjbaldon.banksys.repository.AccountRepository;
import com.github.fjbaldon.banksys.repository.TransactionRepository;
import com.github.fjbaldon.banksys.repository.UserRepository;
import com.github.fjbaldon.banksys.service.AccountService;
import com.github.fjbaldon.banksys.service.TransactionService;
import com.github.fjbaldon.banksys.service.UserService;
import com.github.fjbaldon.banksys.view.account.*;
import com.github.fjbaldon.banksys.view.user.UserView;

import java.math.BigDecimal;
import java.util.List;

/**
 * The UserController class acts as a controller in the MVC (Model-View-Controller) architecture
 * for managing interactions related to user authentication, registration, and account management.
 * It handles user input from the associated UserView and communicates with the UserService and
 * AccountService to perform actions such as login, user registration, and opening new accounts.
 * The class also initializes the necessary views and sets up event listeners for user interactions.
 *
 * The init() method sets up event listeners for various buttons in the UserView and initializes
 * the view to display the login screen. Methods prefixed with "handle" are responsible for handling
 * specific user actions and invoking the appropriate service methods.
 *
 * Note: Error handling is implemented to handle various scenarios, and appropriate messages
 * are displayed in the views. In a real-world application, more sophisticated error handling and
 * logging mechanisms may be necessary.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class UserController {
    public static UserController create(UserService userService,
                                        AccountService accountService,
                                        UserView userView) {
        return new UserController(userService, accountService, userView);
    }

    public void init() {
        userView.getLogin().addShowPasswordCheckBoxListener(e -> userView.getLogin().togglePasswordVisibility());
        userView.getLogin().addLogInButtonListener(e -> handleLogInButton());
        userView.getLogin().addCreateNewUserButtonListener(e -> userView.showCreation());

        userView.getCreation().addShowPasswordCheckBoxListener(e -> userView.getCreation().togglePasswordVisibility());
        userView.getCreation().addCreateUserButtonListener(e -> handleCreateUserButton());
        userView.getCreation().addBackToLogInButtonListener(e -> userView.showLogin());

        userView.getAccounts().addOpenAccountButtonListener(e -> handleOpenAccountButton());
        userView.getAccounts().addCreateNewAccountButtonListener(e -> userView.showAccountCreation(user));
        userView.getAccounts().addBackToLogInButtonListener(e -> userView.showLogin());

        userView.getAccountCreation().addGenerateButtonListener(e -> userView.getAccountCreation().generateAccountNumber());
        userView.getAccountCreation().addCreateAccountButtonListener(e -> handleCreateAccountButton());
        userView.getAccountCreation().addBackToAccountsButtonListener(e -> userView.showAccounts(user));

        userView.showLogin();
    }

    private User user;
    private final UserService userService;
    private final AccountService accountService;
    private final UserView userView;

    private void handleLogInButton() {
        var username = userView.getLogin().getUsername();
        var password = userView.getLogin().getPassword();

        var userToAuth = User.create(username, password);

        var result = userService.authenticate(User.create(username, password));
        switch (result) {
            case OK -> {
                this.user = userToAuth;
                user.setOwnedAccounts(accountService.listAccountsOf(user));
                userView.showAccounts(user);
            }
            case INVALID_CREDENTIALS -> userView.getLogin().displayErrorMessage("Authentication failed");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private void handleCreateUserButton() {
        var username = userView.getCreation().getUsername();
        var password = userView.getCreation().getPassword();

        if (!isValidUsername(username)) {
            userView.getCreation().displayErrorMessage("Username must be at least 8 characters long and not exceed 255 characters");
            return;
        }

        if (!isValidPassword(password)) {
            userView.getCreation().displayErrorMessage("Password must be at least 8 characters long and not exceed 64 characters");
            return;
        }

        var result = userService.register(User.create(username, password));
        switch (result) {
            case OK -> {
                userView.getCreation().displaySuccessMessage("Registration successful! You can now log in");
                userView.showLogin();
            }
            case USERNAME_TAKEN -> userView.getCreation().displayErrorMessage("Username is already taken. Please choose a different one");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private void handleOpenAccountButton() {
        List<Object[]> selectedRows = userView.getAccounts().getSelectedRows();

        if (selectedRows.size() != 1) {
            userView.getAccounts().displayErrorMessage("Please ensure that exactly one account is selected for this action");
            return;
        }

        Object[] row = selectedRows.get(0);
        int accountNumber;
        BigDecimal balance;
        try {
            accountNumber = Integer.parseInt(row[0].toString());
            balance = new BigDecimal(row[1].toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException("FATAL_ERR");
        }

        var account = Account.create(accountNumber, balance, user);

        var connection = Database.instance().getConnection();
        var userRepository = UserRepository.create(connection);
        var accountRepository = AccountRepository.create(userRepository, connection);
        var transactionRepository = TransactionRepository.create(accountRepository, connection);
        var transactionService = TransactionService.create(transactionRepository);
        var accountView = AccountView.create(
                BankSys.instance(),
                ActivitiesPanel.create(),
                DepositPanel.create(),
                WithdrawalPanel.create(),
                TransferalPanel.create(),
                HistoryPanel.create(),
                ManagePanel.create()
        );
        AccountController.create(user, account, accountService, transactionService,  userView, accountView).init();
    }

    private void handleCreateAccountButton() {
        var accountNumberString = userView.getAccountCreation().getAccountNumber();

        if (accountNumberString.isBlank()) {
            userView.getAccountCreation().displayErrorMessage("Invalid account number. Please click on the generate button");
            return;
        }

        int accountNumber;
        try {
            accountNumber = Integer.parseInt(accountNumberString);
        } catch (NumberFormatException e) {
            throw new RuntimeException("FATAL_ERR");
        }

        var newAccount = Account.create(accountNumber, BigDecimal.ZERO, user);

        var result = accountService.register(newAccount);
        switch (result) {
            case OK -> {
                userView.getAccountCreation().displaySuccessMessage("Registration successful!. You can now open the account");
                user.setOwnedAccounts(accountService.listAccountsOf(user));
                userView.showAccounts(user);
            }
            case ACCOUNT_NUMBER_TAKEN -> userView.getAccountCreation().displayErrorMessage("Account number is already taken. Please choose a different one.");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private boolean isValidUsername(String username) {
        return username != null && username.length() >= 8 && username.length() <= 255;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 64;
    }

    private UserController(UserService userService,
                           AccountService accountService,
                           UserView userView) {
        this.userService = userService;
        this.accountService = accountService;
        this.userView = userView;
    }
}
