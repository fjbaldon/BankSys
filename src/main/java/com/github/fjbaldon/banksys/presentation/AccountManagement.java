package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountManagement {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public AccountManagement(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });

        deleteAccountButton.addActionListener(e -> {
            // TODO
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField confirmationField;
    private JButton backButton;
    private JButton deleteAccountButton;
}
