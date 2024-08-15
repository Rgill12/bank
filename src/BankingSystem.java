import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controllers.RecurringTransactionSimulator;
import models.*;
import views.LoginScreen;
import views.RegisterScreen;
import utils.SerializationUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankingSystem {

    public static void main(String[] args) {
        Map<String, User> userDatabase = new HashMap<>();

        // Deserialize users from file
        try {
            List<User> users = SerializationUtil.deserializeUsers("users.ser");
            for (User user : users) {
                userDatabase.put(user.getUsername(), user);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading users. Starting with an empty database.");
            e.printStackTrace();
        }

        // Show a JOptionPane to ask the user if they want to simulate recurring transactions
        int response = JOptionPane.showConfirmDialog(null, "Do you want to simulate recurring transactions?", "Simulation", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            // Start recurring transactions
            RecurringTransactionSimulator simulator = new RecurringTransactionSimulator(userDatabase);
            simulator.start();
        }

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        //If we have users open the login screen, otherwise open the register screen
        if (userDatabase.isEmpty()) {
            SwingUtilities.invokeLater(() -> new RegisterScreen(userDatabase).setVisible(true));
        } else {
            SwingUtilities.invokeLater(() -> new LoginScreen(userDatabase).setVisible(true));
        }
    }
}