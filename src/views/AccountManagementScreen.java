package views;

import controllers.AccountController;
import models.User;
import models.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AccountManagementScreen extends JFrame {
    private User user;
    private AccountController accountController;
    Map<String, User> userDatabase;

    public AccountManagementScreen(User user, Map<String, User> userDatabase) {
        this.user = user;
        this.userDatabase = userDatabase;

        this.accountController = new AccountController(userDatabase,user);
        setLayout(new GridBagLayout());
        setTitle("Account Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add account-management-related components here
        add(new JLabel("Account Management"), gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new CreateAccountListener());
        add(createAccountButton, gbc);

        JButton viewAccountsButton = new JButton("View All Accounts");
        viewAccountsButton.addActionListener(new ViewAccountsListener());
        add(viewAccountsButton, gbc);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(new DeleteAccountListener());
        add(deleteAccountButton, gbc);

        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(new UpdateAccountListener());
        add(updateAccountButton, gbc);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardScreen(user, userDatabase).setVisible(true);
            this.dispose();
        });
        add(backButton, gbc);
    }

    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account ID:");
            String accountName = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account Name:");
            String accountPassword = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account Password:");
            String accountType = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account Type (Personal/Business):");

            if (accountId != null && accountName != null && accountPassword != null && accountType != null) {
                boolean success = accountController.createAccount(user, accountId, accountName, accountPassword, accountType);
                if (success) {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Account created successfully.");
                } else {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Failed to create account.");
                }
            }
        }
    }

    private class ViewAccountsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder accountsInfo = new StringBuilder();
            List<Account> accounts = user.getAccounts();
            if (accounts == null || accounts.isEmpty()) {
                JOptionPane.showMessageDialog(AccountManagementScreen.this, "No accounts found.");
                return;
            }
            for (Account account : accounts) {
                accountsInfo.append(account.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(AccountManagementScreen.this, accountsInfo.toString());
        }
    }

    private class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account ID to delete:");
            if (accountId != null) {
                boolean success = accountController.closeAccount(accountId);
                if (success) {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Account deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Failed to delete account.");
                }
            }
        }
    }

    private class UpdateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter Account ID to update:");
            String newPassword = JOptionPane.showInputDialog(AccountManagementScreen.this, "Enter new password:");
            if (accountId != null && newPassword != null) {
                boolean success = accountController.editAccount(accountId, newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Account updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(AccountManagementScreen.this, "Failed to update account.");
                }
            }
        }
    }
}