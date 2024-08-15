package views;

import controllers.ServiceController;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AccountServicesScreen extends JFrame {
    private User currentUser;
    private Map<String, User> userDatabase;
    private ServiceController serviceController;

    public AccountServicesScreen(User user, Map<String, User> userDatabase) {
        this.currentUser = user;
        this.userDatabase = userDatabase;
        this.serviceController = new ServiceController();

        setLayout(new GridBagLayout());
        setTitle("Account Services");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add account-services-related components here
        JButton checkbookButton = new JButton("Request Checkbook");
        checkbookButton.addActionListener(e -> serviceController.requestCheckbook(currentUser.getUsername()));
        add(checkbookButton, gbc);

        JButton debitCardButton = new JButton("Request Debit Card");
        debitCardButton.addActionListener(e -> serviceController.requestDebitCard(currentUser.getUsername()));
        add(debitCardButton, gbc);

        JButton recurringPaymentButton = new JButton("Set Up Recurring Payment");
        recurringPaymentButton.addActionListener(e -> {
            String toAccountId = JOptionPane.showInputDialog(this, "Enter Target Account ID:");
            String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:");
            if (toAccountId != null && amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                serviceController.setUpRecurringPayment(currentUser.getUsername(), amount, toAccountId);
            }
        });
        add(recurringPaymentButton, gbc);

        JButton directDebitsButton = new JButton("Manage Direct Debits");
        directDebitsButton.addActionListener(e -> serviceController.manageDirectDebits(currentUser.getUsername()));
        add(directDebitsButton, gbc);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardScreen(user, userDatabase).setVisible(true);
            this.dispose();
        });
        add(backButton, gbc);
    }
}