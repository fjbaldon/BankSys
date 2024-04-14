package com.github.fjbaldon.banksys.presentation.view.user;

import com.github.fjbaldon.banksys.business.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountsPanel {
    public static AccountsPanel create() {
        return new AccountsPanel();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JPanel get() {
        if (user == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void update() {
        var tableModel = (DefaultTableModel) accountsTable.getModel();

        if (tableModel.getColumnCount() == 0) {
            accountsTable.setDefaultEditor(Object.class, null);

            var sorter = new TableRowSorter<TableModel>(tableModel);
            accountsTable.setRowSorter(sorter);

            tableModel.addColumn("Account Number");
            tableModel.addColumn("Balance");

            sorter.setComparator(1, Comparator.naturalOrder());
        }

        tableModel.setRowCount(0);
        for (var account : user.getOwnedAccounts()) {
            var accountNumberStr = account.getNumber();
            var balanceStr = account.getBalance();

            tableModel.addRow(new Object[]{accountNumberStr, balanceStr});
        }
    }

    public List<Object[]> getSelectedRows() {
        int[] selectedRowIndices = accountsTable.getSelectedRows();
        var selectedRows = new ArrayList<Object[]>();

        for (int rowIndex : selectedRowIndices) {
            var rowData = new Object[accountsTable.getColumnCount()];
            for (int colIndex = 0; colIndex < accountsTable.getColumnCount(); colIndex++) {
                rowData[colIndex] = accountsTable.getValueAt(rowIndex, colIndex);
            }
            selectedRows.add(rowData);
        }

        return selectedRows;
    }

    public void addOpenAccountButtonListener(ActionListener actionListener) {
        openAccountButton.addActionListener(actionListener);
    }

    public void addCreateNewAccountButtonListener(ActionListener actionListener) {
        createNewAccountButton.addActionListener(actionListener);
    }

    public void addBackToLogInButtonListener(ActionListener actionListener) {
        backToLoginButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private User user;
    private JPanel panel;
    private JButton openAccountButton;
    private JButton backToLoginButton;
    private JTable accountsTable;
    private JButton createNewAccountButton;

    private AccountsPanel() {
    }
}
