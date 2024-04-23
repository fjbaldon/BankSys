package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.AccountService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public enum CustomerAccountsVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final AccountService accountService = AccountService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Customer))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Customer) optional);
    }


    CustomerAccountsVC() {
        manageProfileButton.addActionListener(e -> application.showView(Application.ApplicationPanels.CUSTOMER_MANAGE, customer));

        accountsTable.setDefaultEditor(Object.class, null);
        accountsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = accountsTable.getSelectedRow();
                if (selectedRow != -1) {
                    accountNumberField.setText(accountsTable.getModel().getValueAt(selectedRow, 0).toString());
                    accountTypeField.setText(accountsTable.getModel().getValueAt(selectedRow, 1).toString());
                    balanceField.setText(accountsTable.getModel().getValueAt(selectedRow, 2).toString());
                    interestRateField.setText(accountsTable.getModel().getValueAt(selectedRow, 3).toString());
                }
            }
        });

        openSelectedAccountButton.addActionListener(e -> {
            if (accountsTable.getSelectedRows().length != 1) {
                JOptionPane.showMessageDialog(panel, "Please select a single account from the Accounts table to open its activities.",
                        "Open Account Activities", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountNumberField.getText().isBlank()) {
                JOptionPane.showMessageDialog(panel, "Please select an account from the Accounts table to open its activities.",
                        "Open Account Activities", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Account> account = accountService.getAccountByNumber(accountNumberField.getText());
            if (account.isEmpty())
                throw new IllegalStateException();

            application.showView(Application.ApplicationPanels.ACCOUNT_ACTIVITIES, account.get());
        });

        createNewAccountButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_CREATE, customer));

        logOutButton.addActionListener(e -> application.showView(Application.ApplicationPanels.LOGIN, null));
    }

    private void prepare(Customer customer) {
        this.customer = customer;

        fullNameField.setText(String.format("%s %s. %s", customer.getFirstName(), customer.getMiddleInitial(), customer.getLastName()));
        fullNameField.setEditable(false);
        accountNumberField.setText("");
        accountNumberField.setEditable(false);
        accountTypeField.setText("");
        accountTypeField.setEditable(false);
        balanceField.setText("");
        balanceField.setEditable(false);
        interestRateField.setText("");
        balanceField.setText("");

        String[] cols = {"Account Number", "Type", "Balance", "Interest Rate"};
        DefaultTableModel model = new DefaultTableModel(null, cols);
        accountsTable.setModel(model);
        List<Account> accounts = accountService.getAccountsByCustomerId(customer.getCustomerId());
        accountsTable.setRowSorter(null);
        for (Account account : accounts) {
            if (account.getDeleted())
                continue;
            model.addRow(new Object[]{account.getAccountNumber(), account.getAccountType(), account.getBalance(), account.getInterestRate()});
        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(accountsTable.getModel());
        accountsTable.setRowSorter(sorter);
    }

    private Customer customer;

    private JPanel panel;
    private JTextField fullNameField;
    private JButton manageProfileButton;
    private JTextField accountNumberField;
    private JTextField accountTypeField;
    private JTextField balanceField;
    private JTextField interestRateField;
    private JButton logOutButton;
    private JButton openSelectedAccountButton;
    private JButton createNewAccountButton;
    private JTable accountsTable;
}
