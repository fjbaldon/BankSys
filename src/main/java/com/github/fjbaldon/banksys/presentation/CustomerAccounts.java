package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CustomerAccounts {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerAccounts(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        manageProfileButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_MANAGEMENT_0);
        });
        
        logOutButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_LOGIN);
        });
        
        openAccountButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });
        
        createNewAccountButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_CREATION);
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JButton manageProfileButton;
    private JButton logOutButton;
    private JButton openAccountButton;
    private JButton createNewAccountButton;
    private JTable accountsTable;
    private JTextField customerField;
    private JTextField accountField;
}
