package views;

import models.User;
import utils.SerializationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> accountTypeCombo;
    private Map<String, User> userDatabase;

    public RegisterScreen(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;

        setTitle("Register");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        add(new JLabel("Account Type:"), gbc);
        accountTypeCombo = new JComboBox<>(new String[]{"Personal", "Business"});
        add(accountTypeCombo, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        add(registerButton, gbc);

        JButton loginButton = new JButton("Already have an account? Login");
        loginButton.addActionListener(e -> {
            new LoginScreen(userDatabase).setVisible(true);
            dispose();
        });
        add(loginButton, gbc);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String accountType = (String) accountTypeCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterScreen.this, "Please fill all fields.");
                return;
            }

            if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(RegisterScreen.this, "Username already exists.");
                return;
            }

            userDatabase.put(username, new User(username, password, accountType));
            try {
                SerializationUtil.serializeUsers(List.copyOf(userDatabase.values()), "users.ser");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(RegisterScreen.this, "Failed to save user data.");
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(RegisterScreen.this, "Registration successful! Please log in.");
            new LoginScreen(userDatabase).setVisible(true);
            dispose();
        }
    }
}