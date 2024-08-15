package models;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String type;
    private double amount;
    private double balanceAfter;
    private String date;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.date = getCurrentDateTime();
    }

    @Override
    public String toString() {
        return date + " | " + type + ": " + amount + " | Balance: " + balanceAfter;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
}