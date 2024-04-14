package com.github.fjbaldon.banksys.controller;

import com.github.fjbaldon.banksys.model.Account;
import com.github.fjbaldon.banksys.model.User;
import com.github.fjbaldon.banksys.service.AccountService;
import com.github.fjbaldon.banksys.service.TransactionService;
import com.github.fjbaldon.banksys.view.account.AccountView;
import com.github.fjbaldon.banksys.view.user.UserView;

import java.math.BigDecimal;

/**
 * The AccountController class acts as a controller in the MVC (Model-View-Controller) architecture
 * for managing interactions related to a specific bank account. It handles user input from the
 * associated AccountView and communicates with the AccountService and TransactionService to perform
 * actions such as deposit, withdrawal, transfer, and account deletion. The class also updates the
 * user and account views accordingly.
 *
 * The init() method sets up event listeners for various buttons in the AccountView and initializes
 * the account's transaction history. Methods prefixed with "handle" are responsible for handling
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
public class AccountController {
    public static AccountController create(User user,
                                           Account account,
                                           AccountService accountService,
                                           TransactionService transactionService,
                                           UserView userView,
                                           AccountView accountView) {
        return new AccountController(user, account, accountService, transactionService, userView, accountView);
    }

    public void init() {
        accountView.getActivities().addBackToAccountsButtonListener(e -> userView.showAccounts(user));
        accountView.getActivities().addDepositButtonListener(e -> accountView.showDeposit(account));
        accountView.getActivities().addWithdrawalButtonListener(e -> accountView.showWithdraw(account));
        accountView.getActivities().addTransferalButtonListener(e -> accountView.showTransfer(account));
        accountView.getActivities().addHistoryButtonListener(e -> accountView.showHistory(account));
        accountView.getActivities().addManageButtonListener(e -> accountView.showManage(account));

        accountView.getDeposit().addBackToActivitiesButtonListener(e -> accountView.showActivities(account));
        accountView.getDeposit().addDepositFundsButtonListener(e -> handleDepositFundsButton());

        accountView.getWithdraw().addBackToActivitiesButtonListener(e -> accountView.showActivities(account));
        accountView.getWithdraw().addWithdrawFundsButtonListener(e -> handleWithdrawFundsButton());

        accountView.getTransfer().addBackToActivitiesButtonListener(e -> accountView.showActivities(account));
        accountView.getTransfer().addTransferFundsButtonListener(e -> handleTransferFundsButton());

        accountView.getHistory().addBackToActivitiesButtonListener(e -> accountView.showActivities(account));

        accountView.getManage().addBackToActivitiesButton(e -> accountView.showActivities(account));
        accountView.getManage().addDeleteAccountButtonListener(e -> handleDeleteAccountButton());

        account.setTransactions(transactionService.listTransactionsOf(account));
        accountView.showActivities(account);
    }

    private void handleDepositFundsButton() {
        BigDecimal amount;
        try {
            amount = new BigDecimal(accountView.getDeposit().getAmount());
        } catch (NumberFormatException e) {
            accountView.getDeposit().displayErrorMessage("Invalid deposit amount");
            return;
        }

        var result = accountService.deposit(account, amount);
        switch (result) {
            case OK -> {
                user.setOwnedAccounts(accountService.listAccountsOf(user));
                account.setTransactions(transactionService.listTransactionsOf(account));

                accountView.getDeposit().update();
                accountView.getDeposit().displaySuccessMessage("Deposited ₱" + amount + ". New balance: ₱" + account.getBalance());
            }
            case INVALID_AMOUNT -> accountView.getDeposit().displayErrorMessage("Deposit amount must be greater than zero");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private void handleWithdrawFundsButton() {
        BigDecimal amount;
        try {
            amount = new BigDecimal(accountView.getWithdraw().getAmount());
        } catch (NumberFormatException e) {
            accountView.getWithdraw().displayErrorMessage("Invalid withdrawal amount");
            return;
        }

        var result = accountService.withdraw(account, amount);
        switch (result) {
            case OK -> {
                user.setOwnedAccounts(accountService.listAccountsOf(user));
                account.setTransactions(transactionService.listTransactionsOf(account));

                accountView.getWithdraw().update();
                accountView.getWithdraw().displaySuccessMessage("Withdrew ₱" + amount + ". New balance: ₱" + account.getBalance());
            }
            case INVALID_AMOUNT -> accountView.getWithdraw().displayErrorMessage("Withdrawal amount must be greater than zero");
            case INSUFFICIENT_FUNDS -> accountView.getWithdraw().displayErrorMessage("Insufficient funds for withdrawal");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private void handleTransferFundsButton() {
        BigDecimal amount;
        try {
            amount = new BigDecimal(accountView.getTransfer().getAmount());
        } catch (NumberFormatException e) {
            accountView.getTransfer().displayErrorMessage("Invalid transferal amount");
            return;
        }

        int receivingAccountNumber;
        try {
            receivingAccountNumber = Integer.parseInt(accountView.getTransfer().getReceivingAccountNumber());
        } catch (NumberFormatException e) {
            accountView.getTransfer().displayErrorMessage("Invalid account number format");
            return;
        }

        var result = accountService.transfer(account, receivingAccountNumber, amount);
        switch (result) {
            case OK -> {
                user.setOwnedAccounts(accountService.listAccountsOf(user));
                account.setTransactions(transactionService.listTransactionsOf(account));

                accountView.getTransfer().update();
                accountView.getTransfer().displaySuccessMessage("Transferred ₱" + amount + " to account " + receivingAccountNumber + ". Your new balance: ₱" + account.getBalance());
            }
            case SIMILAR_ACCOUNTS -> accountView.getTransfer().displayErrorMessage("Transferal not available for similar accounts");
            case ACCOUNT_NOT_FOUND -> accountView.getTransfer().displayErrorMessage("Receiving account not found");
            case INVALID_AMOUNT -> accountView.getHistory().displayErrorMessage("Transferal amount must be greater than zero");
            case INSUFFICIENT_FUNDS -> accountView.getTransfer().displayErrorMessage("Insufficient funds for transferal");
            default -> throw new RuntimeException("FATAL_ERR");
        }
    }

    private void handleDeleteAccountButton() {
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            accountView.getManage().displayErrorMessage("Cannot delete the account. Please make sure the account balance is zero before deleting");
            return;
        }

        if (accountService.delete(account) != AccountService.DeletionStatus.OK)
            throw new RuntimeException("FATAL_ERR");

        accountView.getManage().displaySuccessMessage("Account deletion was successful. You will be redirected");
        user.setOwnedAccounts(accountService.listAccountsOf(user));
        userView.showAccounts(user);
    }

    private final User user;
    private final Account account;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserView userView;
    private final AccountView accountView;

    private AccountController(User user,
                              Account account,
                              AccountService accountService,
                              TransactionService transactionService,
                              UserView userView,
                              AccountView accountView) {
        this.user = user;
        this.account = account;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userView = userView;
        this.accountView = accountView;
    }
}
