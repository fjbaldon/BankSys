package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountActivities {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public AccountActivities(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_ACCOUNTS);
        });

        makeADepositButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_DEPOSIT);
        });

        makeAWithdrawalButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_WITHDRAWAL);
        });

        transferFundsButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_TRANSFERRAL);
        });

        transactionHistoryButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_HISTORY);
        });

        manageAccountButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_MANAGEMENT);
        });
    }

    private final JFrame frame;

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
