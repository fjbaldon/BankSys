package com.github.fjbaldon.banksys.presentation.view;

import javax.swing.*;
import java.util.Objects;

public enum ApplicationView {

    INSTANCE;
    public final JFrame FRAME = new JFrame();
    public final LoginView LOGIN = LoginView.INSTANCE;
    public final CustomerView CUSTOMER = CustomerView.INSTANCE;
    public final CustomerCreationView CUSTOMER_CREATION = CustomerCreationView.INSTANCE;
    public final LoginCreationView LOGIN_CREATION = LoginCreationView.INSTANCE;
    public final CustomerManagementView CUSTOMER_MANAGEMENT = CustomerManagementView.INSTANCE;
    public final LoginManagementView LOGIN_MANAGEMENT = LoginManagementView.INSTANCE;
    public final AccountCreationView ACCOUNT_CREATION = AccountCreationView.INSTANCE;
    public final AccountActivitiesView ACCOUNT_ACTIVITIES = AccountActivitiesView.INSTANCE;
    public final AccountDepositView ACCOUNT_DEPOSIT = AccountDepositView.INSTANCE;
    public final AccountWithdrawalView ACCOUNT_WITHDRAWAL = AccountWithdrawalView.INSTANCE;
    public final AccountTransferralView ACCOUNT_TRANSFERRAL = AccountTransferralView.INSTANCE;
    public final AccountHistoryView ACCOUNT_HISTORY = AccountHistoryView.INSTANCE;
    public final AccountManagementView ACCOUNT_MANAGEMENT = AccountManagementView.INSTANCE;

    public void display() {
        if (FRAME.isVisible())
            return;

        FRAME.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icon.png"))).getImage());
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setTitle("BankSys - Banking System");
        FRAME.setSize(800, 600);
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);

        LOGIN.show();
    }
}
