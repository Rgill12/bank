package models;

import java.io.Serial;
import java.io.Serializable;

public class BusinessAccount extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public BusinessAccount(String id, String name, String password) {
        super(id, name, password);
    }

    @Override
    public String toString() {
        return "Business " + super.toString();
    }
}
