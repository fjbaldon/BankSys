package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountCreation {
    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public AccountCreation(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        generateButton.addActionListener(e -> {
            // TODO
        });

        createAccountButton.addActionListener(e -> {
            // TODO
        });

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
    private JTextField accountNumberField;
    private JComboBox accountTypeBox;
    private JButton generateButton;
    private JButton createAccountButton;
    private JButton backButton;
}
