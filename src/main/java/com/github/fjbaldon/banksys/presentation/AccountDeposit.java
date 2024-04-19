package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountDeposit {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public AccountDeposit(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });

        depositFundsButton.addActionListener(e -> {
            // TODO
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton depositFundsButton;
    private JButton backButton;
}
