package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.CustomerService;

import javax.swing.*;
import java.util.Optional;

public enum AccountActivitiesVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Account))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Account) optional);
    }

    AccountActivitiesVC() {
        makeADepositButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_DEPOSIT, account));

        makeAWithdrawalButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_WITHDRAWAL, account));

        transferFundsButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_TRANSFER, account));

        transactionHistoryButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_HISTORY, account));

        manageAccountButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_MANAGE, account));

        backButton.addActionListener(e -> {
            Optional<Customer> customer = customerService.getCustomerByAccountNumber(accountNumberField.getText());
            if (customer.isEmpty())
                throw new IllegalStateException();
            application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer.get());
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
    }

    private Account account;

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backButton;
    private JButton makeADepositButton;
    private JButton makeAWithdrawalButton;
    private JButton transferFundsButton;
    private JButton transactionHistoryButton;
    private JButton manageAccountButton;
    private JTextField accountTypeField;
    private JTextField interestRateField;
}
