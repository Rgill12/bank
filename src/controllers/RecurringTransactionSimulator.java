package controllers;

import models.Account;
import models.User;

import javax.swing.*;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RecurringTransactionSimulator {
    private Map<String, User> userDatabase;
    private Timer timer;

    public RecurringTransactionSimulator(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;
        this.timer = new Timer(true);
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (User user : userDatabase.values()) {
                    for (Account account : user.getAccounts()) {
                        account.deposit(100);
                        account.withdraw(50);
                    }
                }
                JOptionPane.showMessageDialog(null, "Recurring transactions processed.");
            }
        }, 0, 60000);
    }

    public void stop() {
        timer.cancel();
    }
}
