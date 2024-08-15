package views;

import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class DashboardScreen extends JFrame {
    private User currentUser;
    Map<String, User> userDatabase;

    public DashboardScreen(User user, Map<String, User> userDatabase) {
        this.currentUser = user;
        this.userDatabase = userDatabase;

        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, gbc);

        addButton("View Transactions", new TransactionScreen(currentUser, userDatabase), gbc);
        addButton("Manage Accounts", new AccountManagementScreen(currentUser, userDatabase), gbc);
        addButton("Account Services", new AccountServicesScreen(currentUser, userDatabase), gbc);
        addButton("Logout", e -> {
            new LoginScreen(userDatabase).setVisible(true);
            dispose();
        }, gbc);
    }

    private void addButton(String text, JFrame panel, GridBagConstraints gbc) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            panel.setVisible(true);
            dispose();
        });
        add(button, gbc);
    }

    private void addButton(String text, ActionListener listener, GridBagConstraints gbc) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        add(button, gbc);
    }
}

