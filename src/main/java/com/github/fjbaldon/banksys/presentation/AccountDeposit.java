package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountDeposit {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public AccountDeposit(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });

        depositFundsButton.addActionListener(e -> {
            // TODO
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton depositFundsButton;
    private JButton backButton;
}
