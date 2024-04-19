package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountActivities {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public AccountActivities(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
        });

        makeADepositButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_DEPOSIT);
        });

        makeAWithdrawalButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_WITHDRAWAL);
        });

        transferFundsButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_TRANSFERRAL);
        });

        transactionHistoryButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_HISTORY);
        });

        manageAccountButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_MANAGEMENT);
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backButton;
    private JButton makeADepositButton;
    private JButton makeAWithdrawalButton;
    private JButton transferFundsButton;
    private JButton transactionHistoryButton;
    private JButton manageAccountButton;
}
