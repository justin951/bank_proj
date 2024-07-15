package revature.repository;

import revature.entity.Transaction;
import revature.exception.TransactionSQLException;
import revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteTransactionDao implements TransactionDao {

    @Override
    public Transaction createTransaction(Transaction newTransactionInfo) {
        try (Connection conn = DatabaseConnector.createConnection()) {
            String sql = "INSERT INTO transaction (account_id, account_name, transaction_type, transaction_type, transaction_amount, balance, transaction_time) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, newTransactionInfo.getAccount_id());
            ps.setString(2, newTransactionInfo.getAccount_name());
            ps.setString(3, newTransactionInfo.getTransaction_type());
            ps.setBigDecimal(4, newTransactionInfo.getTransaction_amount());
            ps.setBigDecimal(5, newTransactionInfo.getBalance());
            ps.setTimestamp(6, newTransactionInfo.getTransaction_time());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Transaction record failed, no record created");
            }

            try (ResultSet pkeyResultSet = ps.getGeneratedKeys()) {
                if (pkeyResultSet.next()) {
                    int generated_transaction_id = (int) pkeyResultSet.getLong(1);
                    return new Transaction(
                            generated_transaction_id,
                            newTransactionInfo.getAccount_id(),
                            newTransactionInfo.getAccount_name(),
                            newTransactionInfo.getTransaction_type(),
                            newTransactionInfo.getTransaction_amount(),
                            newTransactionInfo.getBalance(),
                            newTransactionInfo.getTransaction_time()
                    );
                } else {
                    throw new SQLException("Create transaction failed, no Id obtained");
                }
            }
        } catch (SQLException ex) {
            throw new TransactionSQLException(ex.getMessage());
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        try (Connection conn = DatabaseConnector.createConnection()) {
            String sql = "SELECT * FROM transaction WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                Transaction transactionRecord = new Transaction();
                transactionRecord.setTransaction_id(rs.getInt("transaction_id"));
                transactionRecord.setAccount_id(rs.getInt("account_id"));
                transactionRecord.setAccount_name(rs.getString("account_name"));
                transactionRecord.setTransaction_type(rs.getString("transaction_type"));
                transactionRecord.setTransaction_amount(rs.getBigDecimal("transaction_amount"));
                transactionRecord.setBalance(rs.getBigDecimal("balance"));
                transactionRecord.setTransaction_time(rs.getTimestamp("transaction_time"));
                transactions.add(transactionRecord);
            }
        } catch (SQLException ex) {
            throw new TransactionSQLException(ex.getMessage());
        }
        return null;
    }
}
