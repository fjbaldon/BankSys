package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.AccountService;
import com.github.fjbaldon.banksys.business.service.TransactionService;
import com.github.fjbaldon.banksys.business.service.exception.TransactionServiceException;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Optional;

public enum AccountWithdrawalVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
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

    AccountWithdrawalVC() {
        backButton.setVerifyInputWhenFocusTarget(false);
        backButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_ACTIVITIES, account));

        withdrawFundsButton.addActionListener(e -> {
            BigDecimal amount;
            try {
                amount = new BigDecimal(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(panel, "Withdrawal amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                transactionService.createWithdrawal(account.getAccountId(), amount, "Removes funds from an account.");
                JOptionPane.showMessageDialog(panel, "Withdrawal successful!");
            } catch (TransactionServiceException ex) {
                JOptionPane.showMessageDialog(panel, "Withdrawal failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Account> updatedAccount = accountService.getAccountById(account.getAccountId());
            if (updatedAccount.isEmpty())
                throw new IllegalStateException();
            application.showView(Application.ApplicationPanels.ACCOUNT_ACTIVITIES, updatedAccount.get());
        });

        balanceField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String balanceString = textField.getText().trim();

                if (balanceString.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter the balance.", "Empty Balance", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (!balanceString.matches("^\\d+(\\.\\d{2})?$")) {
                    JOptionPane.showMessageDialog(panel, "Invalid balance format. Use a positive number with optional decimals (e.g., 100.50).", "Invalid Format", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try {
                    double balance = Double.parseDouble(balanceString);
                    if (balance < 0) {
                        JOptionPane.showMessageDialog(panel, "Balance cannot be negative. Please enter a positive value.", "Invalid Balance", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(panel, "Invalid balance format. Please enter a valid number.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });
    }

    private void prepare(Account account) {
        this.account = account;

        accountNumberField.setText(account.getAccountNumber());
        accountNumberField.setEditable(false);
        accountTypeField.setText(account.getAccountType().toString());
        accountTypeField.setEditable(false);
        balanceField.setText(account.getBalance().toString());
        balanceField.setEditable(false);
        interestRateField.setText(account.getInterestRate().toString());
        interestRateField.setEditable(false);
        amountField.setText("");
    }

    private Account account;

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton backButton;
    private JButton withdrawFundsButton;
    private JTextField accountTypeField;
    private JTextField interestRateField;
}
