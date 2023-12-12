package tk.fjbaldon.banksys.view.account;

import tk.fjbaldon.banksys.model.Account;
import tk.fjbaldon.banksys.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class HistoryPanel {
    public static HistoryPanel create() {
        return new HistoryPanel();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public JPanel get() {
        if (account == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void update() {
        var tableModel = (DefaultTableModel) historyTable.getModel();
        if (tableModel.getColumnCount() == 0) {
            historyTable.setDefaultEditor(Object.class, null);

            var sorter = new TableRowSorter<TableModel>(tableModel);
            historyTable.setRowSorter(sorter);

            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Description");
            tableModel.addColumn("Amount");

            sorter.setComparator(3, Comparator.naturalOrder());
        }

        tableModel.setRowCount(0);
        for (Transaction transaction : account.getTransactions()) {
            var id = transaction.getID();
            var date = transaction.getDate();
            var description = transaction.getDescription();
            var amount = transaction.getAmount();

            tableModel.addRow(new Object[]{id, date, description, amount});
        }
    }

    public void addBackToActivitiesButtonListener(ActionListener actionListener) {
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
    private JButton backToActivitiesButton;
    private JTable historyTable;

    private HistoryPanel() {
    }
}
