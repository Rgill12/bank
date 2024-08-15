package models;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String password;
    protected double balance;
    protected List<Transaction> transactions;
    protected String creationDate;

    public Account(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.creationDate = getCurrentDateTime();
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount, balance));
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
            transactions.add(new Transaction("Failed withdrawal attempt", amount, balance));
        }
        return false;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (balance >= amount) {
            this.withdraw(amount);
            targetAccount.deposit(amount);
            transactions.add(new Transaction("Transfer to " + targetAccount.id, amount, balance));
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
            transactions.add(new Transaction("Failed transfer attempt", amount, balance));
        }
        return false;
    }

    public List<Transaction> getTransactionHistory() {
        return transactions;
    }

    @Override
    public String toString() {
        return "ACC: [ " + id + " ] " + name + " | Balance: " + balance + " | Created on: " + creationDate;
    }

    protected String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getId() {
        return id;
    }
}