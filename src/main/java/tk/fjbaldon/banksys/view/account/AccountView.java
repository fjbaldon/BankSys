package tk.fjbaldon.banksys.view.account;

import tk.fjbaldon.banksys.BankSys;
import tk.fjbaldon.banksys.model.Account;

/**
 * The AccountView class provides methods for displaying different panels related to account activities.
 * It acts as a controller for the UI, coordinating the display of panels for activities, deposit, withdrawal,
 * transfer, history, and management.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class AccountView {
    public static AccountView create(BankSys bankSys,
                                     ActivitiesPanel activitiesPanel,
                                     DepositPanel depositPanel,
                                     WithdrawalPanel withdrawalPanel,
                                     TransferalPanel transferalPanel,
                                     HistoryPanel historyPanel,
                                     ManagePanel managePanel) {
        return new AccountView(bankSys, activitiesPanel, depositPanel, withdrawalPanel, transferalPanel, historyPanel, managePanel);
    }

    public ActivitiesPanel getActivities() {
        return activitiesPanel;
    }

    public DepositPanel getDeposit() {
        return depositPanel;
    }

    public WithdrawalPanel getWithdraw() {
        return withdrawalPanel;
    }

    public TransferalPanel getTransfer() {
        return transferalPanel;
    }

    public HistoryPanel getHistory() {
        return historyPanel;
    }

    public ManagePanel getManage() {
        return managePanel;
    }

    public void showActivities(Account account) {
        activitiesPanel.setAccount(account);
        activitiesPanel.update();

        bankSys.setContentPane(activitiesPanel.get());
        bankSys.revalidate();
    }

    public void showDeposit(Account account) {
        depositPanel.setAccount(account);
        depositPanel.update();

        bankSys.setContentPane(depositPanel.getPanel());
        bankSys.revalidate();
    }

    public void showWithdraw(Account account) {
        withdrawalPanel.setAccount(account);
        withdrawalPanel.update();

        bankSys.setContentPane(withdrawalPanel.get());
        bankSys.revalidate();
    }

    public void showTransfer(Account account) {
        transferalPanel.setAccount(account);
        transferalPanel.update();

        bankSys.setContentPane(transferalPanel.get());
        bankSys.revalidate();
    }

    public void showHistory(Account account) {
        historyPanel.setAccount(account);
        historyPanel.update();

        bankSys.setContentPane(historyPanel.get());
        bankSys.revalidate();
    }

    public void showManage(Account account) {
        managePanel.setAccount(account);
        managePanel.update();

        bankSys.setContentPane(managePanel.get());
        bankSys.revalidate();
    }

    private final BankSys bankSys;
    private final ActivitiesPanel activitiesPanel;
    private final DepositPanel depositPanel;
    private final WithdrawalPanel withdrawalPanel;
    private final TransferalPanel transferalPanel;
    private final HistoryPanel historyPanel;
    private final ManagePanel managePanel;

    private AccountView(BankSys bankSys,
                        ActivitiesPanel activitiesPanel,
                        DepositPanel depositPanel,
                        WithdrawalPanel withdrawalPanel,
                        TransferalPanel transferalPanel,
                        HistoryPanel historyPanel,
                        ManagePanel managePanel) {
        this.bankSys = bankSys;
        this.activitiesPanel = activitiesPanel;
        this.depositPanel = depositPanel;
        this.withdrawalPanel = withdrawalPanel;
        this.transferalPanel = transferalPanel;
        this.historyPanel = historyPanel;
        this.managePanel = managePanel;
    }
}
