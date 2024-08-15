package controllers;

import models.Account;
import models.BusinessAccount;
import models.PersonalAccount;
import models.User;
import utils.SerializationUtil;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountController {
    private Map<String, Account> accounts;
    private Map<String, User> userDatabase;
    private User currentUser;

    public AccountController(Map<String, User> userDatabase, User user) {
        this.userDatabase = userDatabase;
        //get accounts from the current user
        initializeAccounts(user);
    }

    private void initializeAccounts(User user) {
        accounts = new HashMap<>();
        for (Account account : user.getAccounts()) {
            accounts.put(account.getId(), account);
        }
    }

    public boolean createAccount(User user, String id, String name, String password, String accountType) {
        if (accounts.containsKey(id)) {
            JOptionPane.showMessageDialog(new JFrame(), "Account id is taken", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Account newAccount = "Personal".equals(accountType) ? new PersonalAccount(id, name, password) : new BusinessAccount(id, name, password);
        accounts.put(id, newAccount);
        user.addAccount(newAccount);
        serializeUserDatabase();
        return true;
    }

    public boolean editAccount(String id, String newPassword) {
        Account account = accounts.get(id);
        if (account != null) {
            account.setPassword(newPassword);
            serializeUserDatabase();
            return true;
        }
        return false;
    }

    public boolean closeAccount(String id) {
        if (accounts.remove(id) != null) {
            serializeUserDatabase();
            return true;
        }
        return false;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public void serializeUserDatabase() {
        try {
            SerializationUtil.serializeUsers(List.copyOf(userDatabase.values()), "users.ser");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Failed to save user data.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}