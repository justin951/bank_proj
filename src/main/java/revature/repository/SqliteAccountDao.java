package revature.repository;

import revature.entity.Account;
import revature.exception.AccountSQLException;
import revature.exception.UserSQLException;
import revature.utility.DatabaseConnector;

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
            double initialBalance = 0;
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(
                        generated_account_id,
                        newAccountInfo.getAccount_name(),
                        initialBalance,
                        newAccountInfo.getPrimary_user(),
                        null
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
            String sql = "SELECT * FROM account WHERE primary_user = ? OR joint_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account accountRecord = new Account();
                accountRecord.setAccount_id(rs.getInt("account_id"));
                accountRecord.setAccount_name(rs.getString("account_name"));
                accountRecord.setBalance(rs.getDouble("balance"));
                accountRecord.setPrimary_user(rs.getInt("primary_user"));
                accountRecord.setJoint_owner(rs.getInt("joint_user"));
                accounts.add(accountRecord);
            }
            return accounts;
        } catch (SQLException ex) {
            throw new UserSQLException(ex.getMessage());
        }
    }

}
