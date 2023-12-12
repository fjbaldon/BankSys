package tk.fjbaldon.banksys.view.account;

import tk.fjbaldon.banksys.model.Account;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DepositPanel {
    public static DepositPanel create() {
        return new DepositPanel();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public JPanel getPanel() {
        if (account == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void update() {
        accountNumberField.setText(String.valueOf(account.getNumber()));
        balanceField.setText(String.valueOf(account.getBalance()));
        amountField.setText("");
    }

    public String getAmount() {
        return amountField.getText();
    }

    public void addBackToActivitiesButtonListener(ActionListener actionListener) {
        backToActivitiesButton.addActionListener(actionListener);
    }

    public void addDepositFundsButtonListener(ActionListener actionListener) {
        depositFundsButton.addActionListener(actionListener);
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
    private JTextField balanceField;
    private JButton backToActivitiesButton;
    private JButton depositFundsButton;
    private JTextField amountField;

    private DepositPanel() {
    }
}
