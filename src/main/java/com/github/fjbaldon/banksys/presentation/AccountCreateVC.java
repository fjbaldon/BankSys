package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.AccountService;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public enum AccountCreateVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final AccountService accountService = AccountService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Customer))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Customer) optional);
    }

    AccountCreateVC() {
        accountNumberField.setEditable(false);
        accountTypeBox.setEditable(false);
        for (Account.AccountType accountType : Account.AccountType.values())
            accountTypeBox.addItem(accountType);

        generateButton.addActionListener(e -> {
            Random random = new Random();
            StringBuilder accountNumber = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int digit = random.nextInt(10);
                accountNumber.append(digit);
            }

            accountNumberField.setText(accountNumber.toString());
        });

        createAccountButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            Account.AccountType accountType = Objects.requireNonNull((Account.AccountType) accountTypeBox.getSelectedItem());

            Optional<Account> account = accountService.getAccountByNumber(accountNumber);
            if (account.isPresent()) {
                JOptionPane.showMessageDialog(panel, "Account number " + accountNumber + " already exists. Please generate a new account number and try again.", "Duplicate Account Number", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal interestRate;
            switch (accountType) {
                case Checking:
                    interestRate = BigDecimal.ZERO;
                    break;
                case Loan:
                    interestRate = new BigDecimal("5.00");
                    break;
                case Savings:
                    interestRate = new BigDecimal("1.25");
                    break;
                default:
                    JOptionPane.showMessageDialog(panel, "Invalid account type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            accountService.createAccount(accountNumber, accountType, BigDecimal.ZERO, interestRate, customer.getCustomerId());
            JOptionPane.showMessageDialog(panel, "Account created successfully! Account number: " + accountNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
            application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer);
        });

        backButton.addActionListener(e -> application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer));
    }

    private void prepare(Customer customer) {
        this.customer = customer;

        generateButton.doClick();
    }

    private Customer customer;

    private JPanel panel;
    private JTextField accountNumberField;
    private JComboBox<Account.AccountType> accountTypeBox;
    private JButton generateButton;
    private JButton createAccountButton;
    private JButton backButton;
}
