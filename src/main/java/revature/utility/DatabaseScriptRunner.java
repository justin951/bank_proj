package revature.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DatabaseScriptRunner {

    public static void main(String[] args) {
        /*
            Paths has a method that lets us return a file path as a Path object we
            can then provide to the Files class in order to streamline returning
            a Stream of String data that is the SQL we want to execute
        */
        // get Path data
        Path sqlPath = Paths.get("src/main/resources/bank_setup_reset_script.sql");
        try {
            try (
                    // create connection object in try-with-resources block
                    Connection connection = DatabaseConnector.createConnection();
                    // create a Stream that has SQL lines saved as String data
                    Stream<String> lines = Files.lines(sqlPath);
            ) {
                // by setting auto commit to false can execute multiple statements
                // and then commit them together, ensuring all data or no data is updated
                connection.setAutoCommit(false);
                StringBuilder sqlBuilder = new StringBuilder();
                // this line loops through stream and appends each line to StringBuilder object
                lines.forEach(line -> sqlBuilder.append(line));
                String sql = sqlBuilder.toString();
                /*
                can now split the sql into individual statements
                the \\R character is a more comprehensive newline indicator
                this means new line, carriage return, and other characters are referenced
                */
                String[] statements = sql.split(";");
                // for each statement we need executed we make a Statement object and call
                // the executeUpdate method, passing in our sql String statement
                for (String statement : statements) {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(statement);
                }
                connection.commit();
            }
            // need to catch potential SQL and IO Exceptions
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
