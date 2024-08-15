package models;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String accountType;
    private String accountCreationDate;
    private List<Account> accounts;

    public User(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        accounts = new ArrayList<>();
        this.accountCreationDate = getCurrentDateTime();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountCreationDate() {
        return accountCreationDate;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}