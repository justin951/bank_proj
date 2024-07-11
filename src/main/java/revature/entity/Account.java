package revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    // VARIABLES
    private int account_id;
    private String account_name;
    private double balance;
    private int primary_user;
    private Integer joint_owner;

    // CONSTRUCTORS
    public Account() {
    }

    public Account(String account_name, double balance, int primary_user, Integer joint_owner) {
        this.account_name = account_name;
        this.balance = balance;
        this.primary_user = primary_user;
        this.joint_owner = joint_owner;
    }

    public Account(int account_id, String account_name, double balance, int primary_user, Integer joint_owner) {
        this.account_id = account_id;
        this.account_name = account_name;
        this.balance = balance;
        this.primary_user = primary_user;
        this.joint_owner = joint_owner;
    }

    // GETTERS SETTERS


    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPrimary_user() {
        return primary_user;
    }

    public void setPrimary_user(int primary_user) {
        this.primary_user = primary_user;
    }

    public Integer getJoint_owner() {
        return joint_owner;
    }

    public void setJoint_owner(Integer joint_owner) {
        this.joint_owner = joint_owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return account_id == account.account_id && Double.compare(balance, account.balance) == 0 && primary_user == account.primary_user && Objects.equals(joint_owner, account.joint_owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, balance, primary_user, joint_owner);
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", balance=" + balance +
                ", primary_user=" + primary_user +
                ", joint_owner=" + joint_owner +
                '}';
    }
}



