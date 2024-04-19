package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Accounts {

    public void show() {
        banksys.setContentPane(panel);
        banksys.revalidate();
    }

    public Accounts(BankSys banksys, LogIn logIn) {
        this.banksys = Objects.requireNonNull(banksys);

        manageProfileButton.addActionListener(e -> {
            // TODO

            JPanel cards = (JPanel) banksys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_MANAGEMENT);
        });
        
        logOutButton.addActionListener(e -> {
            logIn.clearLogInInfo();

            JPanel cards = (JPanel) banksys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.LOGIN);
        });
        
        openAccountButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) banksys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_ACTIVITIES);
        });
        
        createNewAccountButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) banksys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNT_CREATION);
        });
    }

    private final BankSys banksys;

    public JPanel panel;
    private JButton manageProfileButton;
    private JButton logOutButton;
    private JButton openAccountButton;
    private JButton createNewAccountButton;
    private JTable accountsTable;
    private JTextField customerField;
    private JTextField accountField;
}
