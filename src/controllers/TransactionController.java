package controllers;

import models.Account;
import models.Transaction;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class TransactionController {
    private AccountController accountController;

    public TransactionController(AccountController accountController) {
        this.accountController = accountController;
    }

    public boolean deposit(String accountId, double amount) {
        Account account = accountController.getAccount(accountId);
        if (account != null) {
            account.deposit(amount);
            accountController.serializeUserDatabase();
            return true;
        }
        JOptionPane.showMessageDialog(new JFrame(), "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public boolean withdraw(String accountId, double amount) {
        Account account = accountController.getAccount(accountId);
        if (account != null) {
            if (account.withdraw(amount)){
                accountController.serializeUserDatabase();
                JOptionPane.showMessageDialog(new JFrame(), "Withdrawal successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }else {
                JOptionPane.showMessageDialog(new JFrame(), "Withdrawal failed", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = accountController.getAccount(fromAccountId);
        Account toAccount = accountController.getAccount(toAccountId);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.transfer(toAccount, amount)){
                accountController.serializeUserDatabase();
                JOptionPane.showMessageDialog(new JFrame(), "Transfer successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }else {
                JOptionPane.showMessageDialog(new JFrame(), "Transfer failed", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        Account account = accountController.getAccount(accountId);
        if (account != null) {
            return account.getTransactionHistory();
        }
        return Collections.emptyList();
    }
}