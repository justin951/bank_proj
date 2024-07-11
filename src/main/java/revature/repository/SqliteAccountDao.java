package revature.repository;

import revature.entity.Account;
import revature.exception.AccountSQLException;
import revature.utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteAccountDao implements AccountDao {

    @Override
    public Account createAccount(Account newAccountInfo){
        String sql = "insert into account (balance, owner) values (?, ?)";
        try (Connection conn = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, newAccountInfo.getBalance());
            preparedStatement.setInt(2, newAccountInfo.getPrimary_user());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(
                        generated_account_id,
                        newAccountInfo.getBalance(),
                        newAccountInfo.getPrimary_user(),
                        null
                );
            }
            throw new AccountSQLException("Account could not be created: please try again");
        } catch (SQLException ex) {
            throw new AccountSQLException(ex.getMessage());
        }
    }

//    @Override
//    public List<Account> getAllAccounts() {
//
//    }

}
