package com.github.fjbaldon.banksys;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.github.fjbaldon.banksys.controller.UserController;
import com.github.fjbaldon.banksys.database.Database;
import com.github.fjbaldon.banksys.repository.AccountRepository;
import com.github.fjbaldon.banksys.repository.TransactionRepository;
import com.github.fjbaldon.banksys.repository.UserRepository;
import com.github.fjbaldon.banksys.service.AccountService;
import com.github.fjbaldon.banksys.service.UserService;
import com.github.fjbaldon.banksys.view.user.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main class for the BankSys application.
 * Extends JFrame to create the main graphical user interface.
 * This class sets up the application, initializes the database connection,
 * and creates instances of necessary services, repositories, and views.
 * The GUI components are created using Swing.
 * The application follows the MVC (Model-View-Controller) design pattern.
 * The main method schedules the GUI creation on the event dispatch thread.
 *
 * The BankSys class serves as the entry point for the application,
 * creating and organizing the various components of the banking system.
 * It also handles the initialization of the database connection and
 * the termination of the application upon window closing.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class BankSys extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankSys::start);
    }

    public static void start() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.err.println(ex.getMessage());
            }
        }

        var connection = Database.instance().getConnection();
        var userRepository = UserRepository.create(connection);
        var accountRepository = AccountRepository.create(userRepository, connection);
        var transactionRepository = TransactionRepository.create(accountRepository, connection);
        var userService = UserService.create(userRepository);
        var accountService = AccountService.create(accountRepository, transactionRepository);
        var userView = UserView.create(
                BankSys.instance(),
                LoginPanel.create(),
                CreationPanel.create(),
                AccountsPanel.create(),
                AccountCreationPanel.create()
        );
        UserController.create(userService, accountService, userView).init();
    }

    public static BankSys instance() {
        return INSTANCE;
    }
    private static final BankSys INSTANCE = new BankSys();

    private BankSys() {
        setTitle("BankSys");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Database.instance().closeConnection();
                System.exit(0);
            }
        });
    }
}