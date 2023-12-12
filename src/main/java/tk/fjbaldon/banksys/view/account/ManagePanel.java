package tk.fjbaldon.banksys.view.account;

import tk.fjbaldon.banksys.model.Account;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ManagePanel {
    public static ManagePanel create() {
        return new ManagePanel();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void update() {
        accountNumberField.setText(String.valueOf(account.getNumber()));
        balanceField.setText(String.valueOf(account.getBalance()));
    }

    public JPanel get() {
        if (account == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void addDeleteAccountButtonListener(ActionListener actionListener) {
        deleteAccountButton.addActionListener(actionListener);
    }

    public void addBackToActivitiesButton(ActionListener actionListener) {
        backToActivitiesButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private Account account;
    private JPanel panel;
    private JTextField accountNumberField;
    private JButton backToActivitiesButton;
    private JButton deleteAccountButton;
    private JTextField balanceField;

    private ManagePanel() {
    }
}
