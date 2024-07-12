package revature.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Account implements Serializable {

    // VARIABLES
    private int account_id;
    private String account_name;
    private BigDecimal balance;
    private int primary_user;
    private Integer joint_owner;

    // CONSTRUCTORS
    public Account() {
    }

    public Account(String account_name, BigDecimal balance, int primary_user, Integer joint_owner) {
        this.account_name = account_name;
        this.balance = balance;
        this.primary_user = primary_user;
        this.joint_owner = joint_owner;
    }

    public Account(int account_id, String account_name, BigDecimal balance, int primary_user, Integer joint_owner) {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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
        return account_id == account.account_id && primary_user == account.primary_user && Objects.equals(account_name, account.account_name) && Objects.equals(balance, account.balance) && Objects.equals(joint_owner, account.joint_owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, account_name, balance, primary_user, joint_owner);
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", account_name='" + account_name + '\'' +
                ", balance=" + balance +
                ", primary_user=" + primary_user +
                ", joint_owner=" + joint_owner +
                '}';
    }

    public String toShortString() {
        return "{ account: " + account_name +
                ", balance: " + String.format("%.2f", balance) +
                ", primary user: " + primary_user +
                ", joint owner: " + (joint_owner != null ? joint_owner.toString() : "none") + " }";
    }
}



