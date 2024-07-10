package revature.repository;

import revature.entity.User;
import revature.exception.UserSQLException;
import revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements UserDao {

    @Override
    public User createUser(User newUserCredentials) {
        // need sql statement
        // need connection object
        // need to return the newly generated user
        String sql = "insert into user values (?, ?)";
        try (Connection conn = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newUserCredentials.getUsername());
            preparedStatement.setString(2, newUserCredentials.getPassword());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return newUserCredentials;
            }
            throw new UserSQLException("User could not be created: please try again");
        } catch (SQLException ex) {
            throw new UserSQLException(ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        // need sql statement
        // need a connection object
        // need a
        String sql = "select * from user";
        try (Connection conn = DatabaseConnector.createConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                System.out.println("printme");
                User userRecord = new User();
                userRecord.setUsername(resultSet.getString("username"));
                userRecord.setPassword(resultSet.getString("password"));
                users.add(userRecord);
            }
            return users;
        } catch (SQLException ex) {
            throw new UserSQLException(ex.getMessage());
        }
    }
}
