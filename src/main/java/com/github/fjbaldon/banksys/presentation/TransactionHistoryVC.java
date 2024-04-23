package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.business.service.AccountService;
import com.github.fjbaldon.banksys.business.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public enum TransactionHistoryVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final AccountService accountService = AccountService.INSTANCE;
    private final TransactionService transactionService = TransactionService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Account))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Account) optional);
    }

    TransactionHistoryVC() {
        backButton.addActionListener(e -> application.showView(Application.ApplicationPanels.ACCOUNT_ACTIVITIES, account));

        historyTable.setDefaultEditor(Object.class, null);
        historyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = historyTable.getSelectedRow();
                if (selectedRow != -1) {
                    transactionIdField.setText(historyTable.getModel().getValueAt(selectedRow, 0).toString());
                    transactionTypeField.setText(historyTable.getModel().getValueAt(selectedRow, 1).toString());
                    amountField.setText(historyTable.getModel().getValueAt(selectedRow, 2).toString());
                    createdAtField.setText(historyTable.getModel().getValueAt(selectedRow, 3).toString());
                    senderField.setText(historyTable.getModel().getValueAt(selectedRow, 4).toString());
                    receiverField.setText(historyTable.getModel().getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    private void prepare(Account account) {
        this.account = account;

        transactionIdField.setText("");
        transactionIdField.setEditable(false);
        transactionTypeField.setText("");
        transactionTypeField.setEditable(false);
        amountField.setText("");
        amountField.setEditable(false);
        createdAtField.setText("");
        createdAtField.setEditable(false);
        senderField.setText("");
        senderField.setEditable(false);
        receiverField.setText("");
        receiverField.setEditable(false);

        String[] cols = {"ID", "Type", "Amount", "Created At", "Sender", "Receiver"};
        DefaultTableModel model = new DefaultTableModel(null, cols);
        historyTable.setModel(model);
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(account.getAccountId());
        historyTable.setRowSorter(null);
        for (Transaction transaction : transactions) {
            String fromAccountNumber = "";
            if (transaction.getFromAccountId() != null) {
                Optional<Account> fromAccount = accountService.getAccountById(transaction.getFromAccountId());
                if (fromAccount.isPresent())
                    fromAccountNumber = fromAccount.get().getAccountNumber();
            }

            String toAccountNumber = "";
            if (transaction.getToAccountId() != null) {
                Optional<Account> toAccount = accountService.getAccountById(transaction.getToAccountId());
                if (toAccount.isPresent())
                    toAccountNumber = toAccount.get().getAccountNumber();
            }

            model.addRow(new Object[]{
                    transaction.getTransactionId(),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    transaction.getCreatedAt(),
                    fromAccountNumber,
                    toAccountNumber
            });
        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(historyTable.getModel());
        historyTable.setRowSorter(sorter);
    }

    private Account account;

    private JPanel panel;
    private JTextField transactionIdField;
    private JTextField transactionTypeField;
    private JButton backButton;
    private JTable historyTable;
    private JTextField amountField;
    private JTextField createdAtField;
    private JTextField senderField;
    private JTextField receiverField;
}
