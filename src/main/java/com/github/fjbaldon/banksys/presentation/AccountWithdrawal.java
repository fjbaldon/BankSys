package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountWithdrawal {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public AccountWithdrawal(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });

        withdrawFundsButton.addActionListener(e -> {
            // TODO
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backButton;
    private JTextField amountField;
    private JButton withdrawFundsButton;
}
