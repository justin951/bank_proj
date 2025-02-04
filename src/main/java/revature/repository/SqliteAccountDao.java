package revature.repository;

import revature.entity.Account;
import revature.exception.AccountSQLException;
import revature.exception.DeleteAccountException;
import revature.exception.UserSQLException;
import revature.utility.DatabaseConnector;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteAccountDao implements AccountDao {

    @Override
    public Account createAccount(Account newAccountInfo) {
        String sql = "insert into account (primary_user, account_name) values (?, ?)";
        try (Connection conn = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, newAccountInfo.getPrimary_user());
            preparedStatement.setString(2, newAccountInfo.getAccount_name());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            BigDecimal initialBalance = BigDecimal.valueOf(0);
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(
                        generated_account_id,
                        newAccountInfo.getAccount_name(),
                        initialBalance,
                        newAccountInfo.getPrimary_user()
                );
            }
            throw new AccountSQLException("Account could not be created: please try again");
        } catch (SQLException ex) {
            throw new AccountSQLException(ex.getMessage());
        }
    }

    @Override
    public List<Account> getUserAccounts(int userId) {
        try (Connection conn = DatabaseConnector.createConnection()) {
            String sql = "SELECT * FROM account WHERE primary_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account accountRecord = new Account();
                accountRecord.setAccount_id(rs.getInt("account_id"));
                accountRecord.setAccount_name(rs.getString("account_name"));
                accountRecord.setBalance(rs.getBigDecimal("balance"));
                accountRecord.setPrimary_user(rs.getInt("primary_user"));
                accounts.add(accountRecord);
            }
            return accounts;
        } catch (SQLException ex) {
            throw new UserSQLException(ex.getMessage());
        }
    }

    @Override
    public Account accountTransaction(Account account, BigDecimal newBalance) {
        try (Connection connection = DatabaseConnector.createConnection()) {
            String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, newBalance);
            ps.setInt(2, account.getAccount_id());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                return getAccountById(account.getAccount_id());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public Account getAccountById(int Id) {
        try (Connection conn = DatabaseConnector.createConnection()) {
            String sql = "SELECT * from Account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setAccount_name(rs.getString("account_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setPrimary_user(rs.getInt("primary_user"));
                return account;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String deleteAccountById(int Id) {
        try (Connection conn = DatabaseConnector.createConnection()) {
            Account account = getAccountById(Id);
            String sql = "DELETE FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                return "Deleted account: " + account.toShortString();
            } else {
                return "No account found with ID " + Id;
            }
        } catch (SQLException ex) {
            throw new DeleteAccountException(ex.getMessage());
        }
    }
}
