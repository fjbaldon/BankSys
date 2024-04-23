package com.github.fjbaldon.banksys;

import com.github.fjbaldon.banksys.presentation.Application;

import javax.swing.*;

public final class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }

            Application.INSTANCE.display();
        });
    }
}