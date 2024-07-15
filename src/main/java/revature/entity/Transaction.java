package revature.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {
    private int transaction_id;
    private int account_id;
    private String account_name;
    private String transaction_type;
    private BigDecimal transaction_amount;
    private BigDecimal balance;
    private Timestamp transaction_time;

    public Transaction() {
    }

    public Transaction(int account_id, String account_name, String transaction_type, BigDecimal transaction_amount, BigDecimal balance, Timestamp transaction_time) {
        this.account_id = account_id;
        this.account_name = account_name;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.balance = balance;
        this.transaction_time = transaction_time;
    }

    public Transaction(int transaction_id, int account_id, String account_name, String transaction_type, BigDecimal transaction_amount, BigDecimal balance, Timestamp transaction_time) {
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.account_name = account_name;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.balance = balance;
        this.transaction_time = transaction_time;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

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

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Timestamp transaction_time) {
        this.transaction_time = transaction_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transaction_id == that.transaction_id && account_id == that.account_id && Objects.equals(account_name, that.account_name) && Objects.equals(transaction_type, that.transaction_type) && Objects.equals(transaction_amount, that.transaction_amount) && Objects.equals(balance, that.balance) && Objects.equals(transaction_time, that.transaction_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction_id, account_id, account_name, transaction_type, transaction_amount, balance, transaction_time);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transaction_id +
                ", account_id=" + account_id +
                ", account_name='" + account_name + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", transaction_amount=" + transaction_amount +
                ", balance=" + balance +
                ", transaction_time=" + transaction_time +
                '}';
    }
}
