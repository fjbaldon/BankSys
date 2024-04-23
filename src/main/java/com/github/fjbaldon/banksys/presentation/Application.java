package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Model;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Application {

    INSTANCE;
    private final JFrame frame = new JFrame();
    private final Map<ApplicationPanels, ApplicationPanel> panels = new HashMap<>();

    public void showView(ApplicationPanels panel, Model optional) {
        ApplicationPanel currentApplicationPanel = panels.get(panel);
        if (currentApplicationPanel != null)
            currentApplicationPanel.show(frame, optional);
        else
            throw new IllegalArgumentException();
    }

    public void display() {
        if (frame.isVisible())
            return;

        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icon.png"))).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BankSys - Banking System");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initPanels();
        showView(ApplicationPanels.LOGIN, null);
    }

    public enum ApplicationPanels {
        LOGIN,
        CUSTOMER_ACCOUNTS,
        CUSTOMER_CREATE,
        LOGIN_CREATE,
        CUSTOMER_MANAGE,
        LOGIN_MANAGE,
        ACCOUNT_CREATE,
        ACCOUNT_ACTIVITIES,
        ACCOUNT_DEPOSIT,
        ACCOUNT_WITHDRAWAL,
        ACCOUNT_TRANSFER,
        ACCOUNT_HISTORY,
        ACCOUNT_MANAGE
    }

    private void initPanels() {
        panels.put(ApplicationPanels.LOGIN, LoginVC.INSTANCE);
        panels.put(ApplicationPanels.CUSTOMER_ACCOUNTS, CustomerAccountsVC.INSTANCE);
        panels.put(ApplicationPanels.CUSTOMER_CREATE, CustomerCreateVC.INSTANCE);
        panels.put(ApplicationPanels.LOGIN_CREATE, LoginCreateVC.INSTANCE);
        panels.put(ApplicationPanels.CUSTOMER_MANAGE, CustomerManageVC.INSTANCE);
        panels.put(ApplicationPanels.LOGIN_MANAGE, LoginManageVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_CREATE, AccountCreateVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_ACTIVITIES, AccountActivitiesVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_DEPOSIT, AccountDepositVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_WITHDRAWAL, AccountWithdrawalVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_TRANSFER, AccountTransferVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_HISTORY, TransactionHistoryVC.INSTANCE);
        panels.put(ApplicationPanels.ACCOUNT_MANAGE, AccountManageVC.INSTANCE);
    }
}
