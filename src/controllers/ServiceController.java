package controllers;

import javax.swing.*;

public class ServiceController {
    public void requestCheckbook(String accountId) {
        JOptionPane.showMessageDialog(null, "Checkbook requested for account: " + accountId);
    }

    public void requestDebitCard(String accountId) {
        JOptionPane.showMessageDialog(null, "Debit card requested for account: " + accountId);
    }

    public void setUpRecurringPayment(String accountId, double amount, String toAccountId) {
        JOptionPane.showMessageDialog(null, "Recurring payment of " + amount + " set up from account: " + accountId + " to account: " + toAccountId);
    }

    public void manageDirectDebits(String accountId) {
        JOptionPane.showMessageDialog(null, "Managing direct debits for account: " + accountId);
    }
}