package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountWithdrawal {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public AccountWithdrawal(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });

        withdrawFundsButton.addActionListener(e -> {
            // TODO
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backButton;
    private JTextField amountField;
    private JButton withdrawFundsButton;
}
