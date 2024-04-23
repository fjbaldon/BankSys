package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.business.service.AccountService;
import com.github.fjbaldon.banksys.business.service.CustomerService;
import com.github.fjbaldon.banksys.business.service.TransactionService;
import com.github.fjbaldon.banksys.business.service.exception.AccountServiceException;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public enum AccountManageVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;
    private final AccountService accountService = AccountService.INSTANCE;
    private final TransactionService transactionService = TransactionService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Account))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Account) optional);
    }

    AccountManageVC() {
        accountNumberField.setEditable(false);
        accountTypeField.setEditable(false);
        balanceField.setEditable(false);
        interestRateField.setEditable(false);

        backButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_ACTIVITIES, account));

        deleteAccountButton.addActionListener(e -> {
            String confirmationText = confirmationField.getText().trim();

            if (confirmationText.equalsIgnoreCase("confirm") && !balanceField.getText().isEmpty()) {
                int confirmDelete = JOptionPane.showConfirmDialog(
                        panel,
                        "Are you sure you want to delete this account? This action cannot be undone.",
                        "Confirm Account Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirmDelete == JOptionPane.YES_OPTION) {
                    try {
                        Optional<Customer> customer = customerService.getCustomerByAccountNumber(account.getAccountNumber());
                        if (customer.isEmpty())
                            throw new IllegalStateException();

                        transactionService.deleteTransactionsByAccountId(account.getAccountId());
                        accountService.deleteAccountById(account.getAccountId());
                        JOptionPane.showMessageDialog(panel, "Account deleted successfully!", "Account Deletion", JOptionPane.INFORMATION_MESSAGE);
                        application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer.get());
                    } catch (AccountServiceException ex) {
                        JOptionPane.showMessageDialog(panel, "Account deletion failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                String message;
                if (confirmationText.equalsIgnoreCase("confirm"))
                    message = "Balance field cannot be empty. Please enter the account balance.";
                else
                    message = "Account deletion not confirmed. Please type 'yes' (case-insensitive) in the confirmation field to proceed.";
                JOptionPane.showMessageDialog(panel, message, "Confirmation Required", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void prepare(Account account) {
        this.account = account;

        accountNumberField.setText(account.getAccountNumber());
        accountTypeField.setText(account.getAccountType().toString());
        balanceField.setText(account.getBalance().toString());
        interestRateField.setText(account.getInterestRate().toString());
        confirmationField.setText("");
    }

    private Account account;
    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField confirmationField;
    private JButton backButton;
    private JButton deleteAccountButton;
    private JTextField accountTypeField;
    private JTextField interestRateField;
}
