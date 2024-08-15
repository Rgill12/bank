package views;

import controllers.AccountController;
import controllers.TransactionController;
import models.User;
import models.Account;
import models.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class TransactionScreen extends JFrame {
    private User currentUser;
    private Map<String, User> userDatabase;
    private TransactionController transactionController;

    public TransactionScreen(User user, Map<String, User> userDatabase) {
        this.currentUser = user;
        this.userDatabase = userDatabase;
        this.transactionController = new TransactionController(new AccountController(userDatabase,user));

        setLayout(new GridBagLayout());
        setTitle("Transactions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add transaction-related components here
        add(new JLabel("Transaction Management"), gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositListener());
        add(depositButton, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawListener());
        add(withdrawButton, gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(new TransferListener());
        add(transferButton, gbc);

        JButton viewTransactionsButton = new JButton("View Transaction History");
        viewTransactionsButton.addActionListener(new ViewTransactionsListener());
        add(viewTransactionsButton, gbc);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardScreen(user, userDatabase).setVisible(true);
            this.dispose();
        });
        add(backButton, gbc);
    }

    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Account ID:");
            String amountStr = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Amount to Deposit:");
            if (accountId != null && amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                boolean success = transactionController.deposit(accountId, amount);
                if (success) {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Deposit successful.");
                } else {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Failed to deposit.");
                }
            }
        }
    }

    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Account ID:");
            String amountStr = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Amount to Withdraw:");
            if (accountId != null && amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                boolean success = transactionController.withdraw(accountId, amount);
                if (success) {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Withdrawal successful.");
                } else {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Failed to withdraw.");
                }
            }
        }
    }

    private class TransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromAccountId = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Source Account ID:");
            String toAccountId = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Target Account ID:");
            String amountStr = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Amount to Transfer:");
            if (fromAccountId != null && toAccountId != null && amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                boolean success = transactionController.transfer(fromAccountId, toAccountId, amount);
                if (success) {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Transfer successful.");
                } else {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "Failed to transfer.");
                }
            }
        }
    }

    private class ViewTransactionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = JOptionPane.showInputDialog(TransactionScreen.this, "Enter Account ID:");
            if (accountId != null) {
                List<Transaction> transactions = transactionController.getTransactionHistory(accountId);
                if (transactions.isEmpty()) {
                    JOptionPane.showMessageDialog(TransactionScreen.this, "No transactions found.");
                } else {
                    StringBuilder transactionsInfo = new StringBuilder();
                    for (Transaction transaction : transactions) {
                        transactionsInfo.append(transaction.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(TransactionScreen.this, transactionsInfo.toString());
                }
            }
        }
    }
}