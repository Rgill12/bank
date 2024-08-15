package views;

import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Map<String, User> userDatabase;

    public LoginScreen(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;

        setTitle("Login");
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

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        add(loginButton, gbc);

        JButton registerButton = new JButton("Don't have an account? Register");
        registerButton.addActionListener(e -> {
            new RegisterScreen(userDatabase).setVisible(true);
            dispose();
        });
        add(registerButton, gbc);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userDatabase.containsKey(username) && userDatabase.get(username).getPassword().equals(password)) {
                new DashboardScreen(userDatabase.get(username), userDatabase).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password.");
            }
        }
    }
}
