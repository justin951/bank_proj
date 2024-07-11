package revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    // VARIABLES
    private int account_id;
    private double balance;
    private int owner;
    private int co_owner;

    // CONSTRUCTORS
    public Account() {}

    public Account(double balance, int owner, int co_owner) {
        this.balance = balance;
        this.owner = owner;
        this.co_owner = co_owner;
    }

    public Account(int account_id, double balance, int owner, int co_owner) {
        this.account_id = account_id;
        this.balance = balance;
        this.owner = owner;
        this.co_owner = co_owner;
    }

    // GETTERS SETTERS
    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getCo_owner() {
        return co_owner;
    }

    public void setCo_owner(int co_owner) {
        this.co_owner = co_owner;
    }

    // OVERRIDES
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return account_id == account.account_id && Double.compare(balance, account.balance) == 0 && owner == account.owner && co_owner == account.co_owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, balance, owner, co_owner);
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", balance=" + balance +
                ", owner=" + owner +
                ", co_owner=" + co_owner +
                '}';
    }
}
